package com.shensou.newpress.bean;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
public class HeadGson extends BaseGson {

    public HeadDetail getResponse() {
        return response;
    }

    public void setResponse(HeadDetail response) {
        this.response = response;
    }

    private HeadDetail response;



    public class HeadDetail{
        private String logo;

        public String getPath() {
            return logo;
        }

        public void setPath(String path) {
            this.logo = path;
        }
    }
}
