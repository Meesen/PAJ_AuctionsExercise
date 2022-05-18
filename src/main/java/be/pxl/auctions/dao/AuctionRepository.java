package be.pxl.auctions.dao;

import be.pxl.auctions.model.Auction;
import be.pxl.auctions.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Optional<Auction> findAcutionById(long auctionId);
    List<Auction> findAll();
}
