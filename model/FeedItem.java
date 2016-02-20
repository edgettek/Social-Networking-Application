package csc296.project02.model;

import java.util.Date;

/**
 * Created by KEdgette1 on 11/13/15.
 */
public class FeedItem implements Comparable<FeedItem> {

    private String email;
    private String text;
    private String picture;
    private Date datePosted;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    //compareTo Used to sort posts reverse chronologically
    @Override
    public int compareTo(FeedItem another) {

        if(getDatePosted().compareTo(another.getDatePosted()) < 0) {
            return 1;
        }
        else {
            if(getDatePosted().compareTo(another.getDatePosted()) == 0) {
                return 0;
            }
            else {
                return -1;
            }
        }

    }
}
