package be.pxl.auctions.rest;

import be.pxl.auctions.model.Bid;
import be.pxl.auctions.model.User;
import be.pxl.auctions.rest.resource.*;
import be.pxl.auctions.service.AuctionService;
import be.pxl.auctions.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("rest/auctions")
public class AuctionRest {

    private static final Logger LOGGER = LogManager.getLogger(AuctionRest.class);

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<AuctionDTO> getAllAuctions() {
        return auctionService.getAllAuctions();
    }

    @GetMapping("{auctionId}")
    public AuctionDTO getAuctionById(@PathVariable("auctionId") long auctionId) {
        return auctionService.getAuctionById(auctionId);
    }


    @PostMapping
    public AuctionDTO addAuction(@RequestBody AuctionCreateResource auctionCreateResource) {
        return auctionService.createAuction(auctionCreateResource);
    }
    @PostMapping("{auctionId}/bids")
    public AuctionDTO addBidToAuction(@PathVariable("auctionId") long auctionId, @RequestBody BidCreateResource bidCreateResource) {
        return auctionService.addBidToAuction(auctionId, bidCreateResource.getEmail(), bidCreateResource.getAmount());
    }
}
