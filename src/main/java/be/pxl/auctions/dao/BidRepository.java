package be.pxl.auctions.dao;

import be.pxl.auctions.model.Bid;
import be.pxl.auctions.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {
    Optional<List<Bid>> findBidsByAuctionId(long auctionId);
    Optional<Bid> findBidById(long userId);
    List<Bid> findAll();
}
