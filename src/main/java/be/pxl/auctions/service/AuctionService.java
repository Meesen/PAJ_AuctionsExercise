package be.pxl.auctions.service;

import be.pxl.auctions.dao.AuctionRepository;
import be.pxl.auctions.dao.UserRepository;
import be.pxl.auctions.model.Auction;
import be.pxl.auctions.model.Bid;
import be.pxl.auctions.model.User;
import be.pxl.auctions.rest.resource.AuctionCreateResource;
import be.pxl.auctions.rest.resource.AuctionDTO;
import be.pxl.auctions.rest.resource.UserCreateResource;
import be.pxl.auctions.rest.resource.UserDTO;
import be.pxl.auctions.util.EmailValidator;
import be.pxl.auctions.util.exception.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuctionService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/uuuu");

    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuctionService(AuctionRepository auctionRepository, UserRepository userRepository) {
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
    }

    public List<AuctionDTO> getAllAuctions() {
        return auctionRepository.findAll().stream().map(this::mapToAuctionResource).collect(Collectors.toList());
    }

    public AuctionDTO getAuctionById(long auctionId) {
        return auctionRepository.findAcutionById(auctionId).map(this::mapToAuctionResource).orElseThrow(()  -> new AuctionNotFoundException("Unable to find auction with id [" + auctionId + "]"));
    }

    public AuctionDTO createAuction(AuctionCreateResource auctionInfo) throws RequiredFieldException, InvalidEmailException, DuplicateEmailException, InvalidDateException {
        if (StringUtils.isBlank(auctionInfo.getDescription())) {
            throw new RequiredFieldException("description");
        }
        if (StringUtils.isBlank(auctionInfo.getEndDate())) {
            throw new RequiredFieldException("end date");
        }
        Auction auction = mapToAuction(auctionInfo);
        if (auction.getEndDate().isBefore(LocalDate.now())) {
            throw new InvalidDateException("endDate cannot be in the past.");
        }
        return mapToAuctionResource(auctionRepository.save(auction));
    }
    public AuctionDTO addBidToAuction(long auctionId, String email, double amount){
        AuctionDTO currentAuction = getAuctionById(auctionId);
        boolean highestBid = true;
        if(currentAuction != null) {
            Optional<User> currentUser = userRepository.findUserByEmail(email);
                for (Bid auctionBid : currentAuction.bids) {
                    if (auctionBid.getAmount() > amount) {
                        highestBid = false;
                }
                if (highestBid) {
                    if(currentUser.isPresent()) {
                        Bid newBid = new Bid(currentUser.get(), LocalDate.now(), amount);
                        List<Bid> bids = currentAuction.getBids();
                        bids.add(newBid);
                        currentAuction.setBids(bids);

                    }
                }
            }
        }
        return currentAuction;
    }


    private AuctionDTO mapToAuctionResource(Auction auction) {
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setId(auction.getId());
        auctionDTO.setBids(auction.getBids());
        auctionDTO.setEndDate(auction.getEndDate());
        auctionDTO.setDescription(auction.getDescription());
        return auctionDTO;
    }

    private Auction mapToAuction(AuctionCreateResource auctionCreateResource) throws InvalidDateException {
        Auction auction = new Auction();
        auction.setDescription(auctionCreateResource.getDescription());
        try {
            auction.setEndDate(LocalDate.parse(auctionCreateResource.getEndDate(), DATE_FORMAT));
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("[" + auction.getEndDate().toString() + "] is not a valid date. Excepted format: dd/mm/yyyy");
        }
        return auction;
    }

}
