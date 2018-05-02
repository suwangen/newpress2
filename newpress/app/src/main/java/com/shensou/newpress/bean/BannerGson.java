package com.shensou.newpress.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/8 0008.
 */
public class BannerGson extends BaseGson {


    /**
     * code : 200
     * response : [{"adv_id":"4","adv_img":"http: //ykapp.plwx.com/uploads/picture/2016-09-23/57e495ca60072.png","adv_url":"http: //www.yh-hospital.com/","adv_title":"郑州友好肝病医院","adv_start_date":"1472140800","adv_end_date":"1504713600","slide_sort":"0"},{"adv_id":"5","adv_img":"http: //ykapp.plwx.com/uploads/picture/2017-01-20/58817c62c5b14.png","adv_url":"http: //www.yh-hospital.com/","adv_title":"欢迎您","adv_start_date":"1473955200","adv_end_date":"1502121600","slide_sort":"0"}]
     * request : []
     */

    private List<ResponseBean> response;

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }


    public static class ResponseBean {
        /**
         * adv_id : 4
         * adv_img : http: //ykapp.plwx.com/uploads/picture/2016-09-23/57e495ca60072.png
         * adv_url : http: //www.yh-hospital.com/
         * adv_title : 郑州友好肝病医院
         * adv_start_date : 1472140800
         * adv_end_date : 1504713600
         * slide_sort : 0
         */

        private String adv_id;
        private String adv_img;
        private String adv_url;
        private String adv_title;
        private String adv_start_date;
        private String adv_end_date;
        private String slide_sort;

        public String getAdv_id() {
            return adv_id;
        }

        public void setAdv_id(String adv_id) {
            this.adv_id = adv_id;
        }

        public String getAdv_img() {
            return adv_img;
        }

        public void setAdv_img(String adv_img) {
            this.adv_img = adv_img;
        }

        public String getAdv_url() {
            return adv_url;
        }

        public void setAdv_url(String adv_url) {
            this.adv_url = adv_url;
        }

        public String getAdv_title() {
            return adv_title;
        }

        public void setAdv_title(String adv_title) {
            this.adv_title = adv_title;
        }

        public String getAdv_start_date() {
            return adv_start_date;
        }

        public void setAdv_start_date(String adv_start_date) {
            this.adv_start_date = adv_start_date;
        }

        public String getAdv_end_date() {
            return adv_end_date;
        }

        public void setAdv_end_date(String adv_end_date) {
            this.adv_end_date = adv_end_date;
        }

        public String getSlide_sort() {
            return slide_sort;
        }

        public void setSlide_sort(String slide_sort) {
            this.slide_sort = slide_sort;
        }
    }
}
