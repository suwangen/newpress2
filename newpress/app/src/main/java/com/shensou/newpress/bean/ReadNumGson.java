package com.shensou.newpress.bean;

/**
 * Created by Administrator on 2017/10/27 0027.
 */

public class ReadNumGson {

    /**
     * message : 未读信息
     * code : 200
     * response : {"status":1}
     * request : {"uid":"512"}
     */

    private String message;
    private int code;
    private ResponseBean response;
    private RequestBean request;

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

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public RequestBean getRequest() {
        return request;
    }

    public void setRequest(RequestBean request) {
        this.request = request;
    }

    public static class ResponseBean {
        /**
         * status : 1
         */

        private int status;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class RequestBean {
        /**
         * uid : 512
         */

        private String uid;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
