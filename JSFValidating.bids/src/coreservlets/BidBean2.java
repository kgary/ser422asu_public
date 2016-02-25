package coreservlets;

import javax.faces.context.*;
import javax.faces.component.*;
import javax.faces.validator.*;
import javax.faces.application.*;

public class BidBean2 {
  private String userID;
  private String keyword;
  private double bidAmount;
  private int bidDuration;

  public String getUserID() { return(userID); }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public String getKeyword() { return(keyword); }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public double getBidAmount() { return(bidAmount); }

  public void setBidAmount(double bidAmount) {
    this.bidAmount = bidAmount;
  }

  public int getBidDuration() { return(bidDuration); }

  public void setBidDuration(int bidDuration) {
    this.bidDuration = bidDuration;
  }

  public String doBid() {
    return("success");
  }

  public void validateBidAmount(FacesContext context,
                                UIComponent componentToValidate,
                                Object value)
      throws ValidatorException {
    double bidAmount = ((Double)value).doubleValue();
    double previousHighestBid = currentHighestBid();
    if (bidAmount <= previousHighestBid) {
      FacesMessage message =
        new FacesMessage("Bid must be higher than current" +
                         "highest bid ($" +
                         previousHighestBid + ").");
      throw new ValidatorException(message);
    }
  }

  public double currentHighestBid() {
    return(0.23); // Get from database in real life
  }
}
