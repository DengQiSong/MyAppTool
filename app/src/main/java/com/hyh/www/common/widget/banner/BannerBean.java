package com.hyh.www.common.widget.banner;

/**
 * 作者：Denqs on 2017/3/6.
 * 广告实体类
 */

public class BannerBean {
    private int id;
    private String ImageName;
    private String ImageUrl;
    private String bannerPath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getBannerPath() {
        return bannerPath;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = bannerPath;
    }
}
