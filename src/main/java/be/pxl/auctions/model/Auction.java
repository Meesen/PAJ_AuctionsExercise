package be.pxl.auctions.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auctions")
public class Auction {

    @Id
    @GeneratedValue
    private long id;
    private String description;
    private LocalDate endDate;
    @OneToMany(mappedBy = "auction")
    public List<Bid> bids = new ArrayList<>();

    public Auction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public Boolean isFinished(){
        /*if(LocalDate.now().isAfter(this.endDate)) {
            return true;
        }
        return false;*/
        return LocalDate.now().isAfter(this.endDate);
    }

    public Bid findHighestBid(){
        Bid highestBid = null;
        for (Bid bid : this.bids) {
            if(bid != null){
                if(bid.getAmount() > highestBid.getAmount()){
                    highestBid = bid;
                }
            }else{
                highestBid = bid;
            }
        }
        return highestBid;
    }

}
