package com.example.models;



public class DataList {
    private long urlId;
    private String urlName;
    private String urlDescription;
    private String creationDate;
    private String lastUpdateDate;
    private String copyUrl;

    public DataList(){

    }

    public DataList(long urlId, String urlName, String urlDescription, String creationDate, String lastUpdateDate,
            String copyUrl) {
        this.urlId = urlId;
        this.urlName = urlName;
        this.urlDescription = urlDescription;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.copyUrl = copyUrl;
    }

    public long getUrlId() {
        return urlId;
    }

    public void setUrlId(long urlId) {
        this.urlId = urlId;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getUrlDescription() {
        return urlDescription;
    }

    public void setUrlDescription(String urlDescription) {
        this.urlDescription = urlDescription;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getCopyUrl() {
        return copyUrl;
    }

    public void setCopyUrl(String copyUrl) {
        this.copyUrl = copyUrl;
    }

    

}
