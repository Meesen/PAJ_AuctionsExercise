package be.pxl.auctions.service;

import be.pxl.auctions.dao.BidRepository;
import be.pxl.auctions.dao.UserRepository;
import be.pxl.auctions.model.Bid;
import be.pxl.auctions.model.User;
import be.pxl.auctions.rest.resource.BidCreateResource;
import be.pxl.auctions.rest.resource.BidDTO;
import be.pxl.auctions.rest.resource.UserCreateResource;
import be.pxl.auctions.rest.resource.UserDTO;
import be.pxl.auctions.util.exception.BidNotFoundException;
import be.pxl.auctions.util.exception.DuplicateEmailException;
import be.pxl.auctions.util.exception.InvalidDateException;
import be.pxl.auctions.util.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BidService {
    BidRepository bidRepository;
    UserRepository userRepository;

    @Autowired
    public BidService(BidRepository bidRepository, UserRepository userRepository)
    {
        this.bidRepository = bidRepository;
        this.userRepository = userRepository;
    }

    public BidDTO createNewBid(BidCreateResource bidInfo){
        Bid bid = mapToBid(bidInfo);
        return mapToBidResource(bidRepository.save(bid));
    }

    public BidDTO getBidById(long bidId){
        return bidRepository.findBidById(bidId).map(this::mapToBidResource).orElseThrow(()  -> new BidNotFoundException("Unable to find Bid with id [" + bidId + "]"));
    }

    public List<BidDTO> getAllBids(){
        return bidRepository.findAll().stream().map(this::mapToBidResource).collect(Collectors.toList());
    }

    private BidDTO mapToBidResource(Bid bid) {
        BidDTO bidDTO = new BidDTO();
        bidDTO.setId(bid.getId());
        bidDTO.setUser(bid.getUser());
        bidDTO.setDate(bid.getDate());
        bidDTO.setAmount(bid.getAmount());
        bidDTO.setAuction(bid.getAuction());
        return bidDTO;
    }

    private Bid mapToBid(BidCreateResource bidCreateResource) throws InvalidDateException {
        Bid bid = new Bid();
        bid.setUser(bidCreateResource.getUser());
        bid.setAmount(bidCreateResource.getAmount());
        bid.setDate(bidCreateResource.getDate());
        return bid;
    }

}
