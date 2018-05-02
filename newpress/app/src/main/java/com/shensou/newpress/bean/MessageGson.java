package com.shensou.newpress.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27 0027.
 */

public class MessageGson {


    /**
     * message : 信息列表
     * code : 200
     * response : [{"id":"1","mobile":"18695698026","title":"title","description":"内容","create_time":"1509097111","uid":"512","is_view":"1"}]
     */

    private String message;
    private String code;
    private List<ResponseBean> response;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * id : 1
         * mobile : 18695698026
         * title : title
         * description : 内容
         * create_time : 1509097111
         * uid : 512
         * is_view : 1
         */

        private String id;
        private String mobile;
        private String title;
        private String description;
        private String create_time;
        private String uid;
        private String is_view;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getIs_view() {
            return is_view;
        }

        public void setIs_view(String is_view) {
            this.is_view = is_view;
        }
    }
}
