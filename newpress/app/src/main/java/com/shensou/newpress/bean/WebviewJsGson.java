package com.shensou.newpress.bean;

/**
 * Created by Administrator on 2018/04/09 0009.
 */

public class WebviewJsGson {

    /**
     * type : weixin_friend
     * title : 标题
     * info : 描述
     * img : 图片链接
     * id : 37
     */

    private String type;
    private String title;
    private String info;
    private String img;
    private String id;
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
