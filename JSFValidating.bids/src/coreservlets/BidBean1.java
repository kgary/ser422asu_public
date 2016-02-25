package coreservlets;
import java.util.*;

public class BidBean1 {
  private String userID = "";
  private String keyword = "";
  private String bidAmount;
  private double numericBidAmount = 0;
  private String bidDuration;
  private int numericBidDuration = 0;
  private List<String> errorMessages = new ArrayList<String>();

  public String getUserID() { return(userID); }
  
  public void setUserID(String userID) {
    this.userID = userID.trim();
  }

  public String getKeyword() { return(keyword); }
  
  public void setKeyword(String keyword) {
    this.keyword = keyword.trim();
  }

  public String getBidAmount() { return(bidAmount); }

  public void setBidAmount(String bidAmount) {
    this.bidAmount = bidAmount;
    try {
      numericBidAmount = Double.parseDouble(bidAmount);
    } catch(NumberFormatException nfe) {}
  }

  public double getNumericBidAmount() {
    return(numericBidAmount);
  }

  public String getBidDuration() { return(bidDuration); }

  public void setBidDuration(String bidDuration) {
    this.bidDuration = bidDuration;
    try {
      numericBidDuration = Integer.parseInt(bidDuration);
    } catch(NumberFormatException nfe) {}
  }

  public int getNumericBidDuration() {
    return(numericBidDuration);
  }

  public String getErrorMessages() {
    String messageList;
    if ((errorMessages == null) || (errorMessages.size() == 0)) {
      messageList = "";
    } else {
      messageList = "<FONT COLOR=RED><B><UL>\n";
      for(String message: errorMessages) {
        messageList = messageList + "<LI>" + message + "\n";
      }
      messageList = messageList + "</UL></B></FONT>\n";
    }
    return(messageList);
  }

  public String doBid() {
    errorMessages = new ArrayList<String>();
    if (getUserID().equals("")) {
      errorMessages.add("UserID required");
    }
    if (getKeyword().equals("")) {
      errorMessages.add("Keyword required");
    }
    if (getNumericBidAmount() <= 0.10) {
      errorMessages.add("Bid amount must be at least $0.10.");
    }
    if (getNumericBidDuration() < 15) {
      errorMessages.add("Duration must be at least 15 days.");
    }
    if (errorMessages.size() > 0) {
      return(null);
    } else {
      return("success");
    }
  }
}
