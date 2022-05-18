package be.pxl.auctions.rest.resource;

import be.pxl.auctions.model.Bid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuctionCreateResource {
    private String description;
    private String endDate;
    public List<Bid> bids = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }
}
