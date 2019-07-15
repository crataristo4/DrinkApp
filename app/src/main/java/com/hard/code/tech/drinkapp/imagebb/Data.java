package com.hard.code.tech.drinkapp.imagebb;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url_viewer")
    @Expose
    private String urlViewer;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("display_url")
    @Expose
    private String displayUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("thumb")
    @Expose
    private Thumb thumb;
    @SerializedName("medium")
    @Expose
    private Medium medium;
    @SerializedName("delete_url")
    @Expose
    private String deleteUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlViewer() {
        return urlViewer;
    }

    public void setUrlViewer(String urlViewer) {
        this.urlViewer = urlViewer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public String getDeleteUrl() {
        return deleteUrl;
    }

    public void setDeleteUrl(String deleteUrl) {
        this.deleteUrl = deleteUrl;
    }

}
