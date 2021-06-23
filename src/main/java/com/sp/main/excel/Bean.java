package com.sp.main.excel;

public class Bean {

    private String phrase;
    private String type;
    private String url;
    private Boolean hot;
    private Boolean common;
    private String category;
    private String icon;
    private String value;
    private String picid;

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    public Boolean getCommon() {
        return common;
    }

    public void setCommon(Boolean common) {
        this.common = common;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPicid() {
        return picid;
    }

    public void setPicid(String picid) {
        this.picid = picid;
    }

    @Override
    public String toString() {
        return "Bean [phrase=" + phrase + ", type=" + type + ", url=" + url + ", hot=" + hot + ", common=" + common + ", category=" + category + ", icon=" + icon + ", value=" + value + ", picid=" + picid + "]";
    }

}