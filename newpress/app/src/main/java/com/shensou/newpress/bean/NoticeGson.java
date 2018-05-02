package com.shensou.newpress.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/08/23 0023.
 */

public class NoticeGson {

    /**
     * message : 最新文章
     * code : 200
     * response : [{"id":"59","title":"通知","excerpt":"通知","thum":"http://jmjx.plwx.com/static/stv/common/images/img.jpg","modified_time":"1503396713","comment_count":"10","tag_list":[]}]
     * request : {"p":"0"}
     */

    private String message;
    private int code;
    private RequestBean request;
    private List<ResponseBean> response;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RequestBean getRequest() {
        return request;
    }

    public void setRequest(RequestBean request) {
        this.request = request;
    }

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class RequestBean {
        /**
         * p : 0
         */

        private String p;

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }
    }

    public static class ResponseBean {
        /**
         * id : 59
         * title : 通知
         * excerpt : 通知
         * thum : http://jmjx.plwx.com/static/stv/common/images/img.jpg
         * modified_time : 1503396713
         * comment_count : 10
         * tag_list : []
         */

        private String id;
        private String title;
        private String excerpt;
        private String thum;
        private String modified_time;
        private String comment_count;
        private List<?> tag_list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getExcerpt() {
            return excerpt;
        }

        public void setExcerpt(String excerpt) {
            this.excerpt = excerpt;
        }

        public String getThum() {
            return thum;
        }

        public void setThum(String thum) {
            this.thum = thum;
        }

        public String getModified_time() {
            return modified_time;
        }

        public void setModified_time(String modified_time) {
            this.modified_time = modified_time;
        }

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public List<?> getTag_list() {
            return tag_list;
        }

        public void setTag_list(List<?> tag_list) {
            this.tag_list = tag_list;
        }
    }
}
