package com.shensou.newpress.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/01/10 0010.
 */

public class MenberTimeLengthGson {

    /**
     * code : 200
     * msg : 下拉数据
     * response : [{"id":1,"time_up":"1个月"},{"id":2,"time_up":"6个月"},{"id":3,"time_up":"12个月"},{"id":4,"time_up":"24个月"},{"id":5,"time_up":"26个月"}]
     */

    private String code;
    private String msg;
    private List<ResponseBean> response;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
         * time_up : 1个月
         */

        private String id;
        private String time_up;
        private boolean isChoose;

        public boolean isChoose() {
            return isChoose;
        }

        public void setChoose(boolean choose) {
            isChoose = choose;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTime_up() {
            return time_up;
        }

        public void setTime_up(String time_up) {
            this.time_up = time_up;
        }
    }
}
