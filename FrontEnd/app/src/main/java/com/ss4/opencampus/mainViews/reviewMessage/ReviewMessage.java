package com.ss4.opencampus.mainViews.reviewMessage;

/**
 * @author Axel Zumwalt
 *
 * The message class describes the behavior of an message object which is created and added to a list
 * when someone comments on the logged in users USpot.
 */
public class ReviewMessage {

    private int USpotId;
    private boolean isRead;

    /**
     * Default Constructor for an OpenCampus ReviewMessage
     */
    public ReviewMessage() {
    }

    /**
     * ReviewMessage constructor
     */
    public ReviewMessage(int USpotId, boolean isRead) {

    }

    public void setUSpotId(int USpotId) {
        this.USpotId = USpotId;
    }

    public int getUSpotId() {
        return this.USpotId;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public boolean getIsRead() {
        return this.isRead;
    }
}
