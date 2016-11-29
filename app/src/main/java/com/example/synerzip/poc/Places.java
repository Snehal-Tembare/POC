package com.example.synerzip.poc;

/**
 * Created by Snehal Tembare on 29/11/16.
 * Copyright © 2016 Synerzip. All rights reserved
 */


public class Places {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String title;
    private String description;
    private   String url;

    public Places(String title,String description,String url) {
        this.title=title;
        this.description=description;
        this.url=url;
    }
}
