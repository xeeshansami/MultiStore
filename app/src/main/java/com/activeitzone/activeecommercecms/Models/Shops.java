
package com.activeitzone.activeecommercecms.Models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shops implements Serializable
{
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("sliders")
    @Expose
    private List<String> sliders = null;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("facebook")
    @Expose
    private Object facebook;
    @SerializedName("google")
    @Expose
    private Object google;
    @SerializedName("twitter")
    @Expose
    private Object twitter;
    @SerializedName("youtube")
    @Expose
    private Object youtube;
    @SerializedName("instagram")
    @Expose
    private Object instagram;
    @SerializedName("links")
    @Expose
    private Links links;
    private final static long serialVersionUID = -9157412665819709086L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<String> getSliders() {
        return sliders;
    }

    public void setSliders(List<String> sliders) {
        this.sliders = sliders;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getFacebook() {
        return facebook;
    }

    public void setFacebook(Object facebook) {
        this.facebook = facebook;
    }

    public Object getGoogle() {
        return google;
    }

    public void setGoogle(Object google) {
        this.google = google;
    }

    public Object getTwitter() {
        return twitter;
    }

    public void setTwitter(Object twitter) {
        this.twitter = twitter;
    }

    public Object getYoutube() {
        return youtube;
    }

    public void setYoutube(Object youtube) {
        this.youtube = youtube;
    }

    public Object getInstagram() {
        return instagram;
    }

    public void setInstagram(Object instagram) {
        this.instagram = instagram;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
