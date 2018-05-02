package com.shensou.newpress.bean;

/**
 * Created by Administrator on 2018/01/08 0008.
 */

public class OrderGson {

    /**
     * code : 200
     * msg : 下单成功
     * response : {"uid":"722","money":"1","order_sn":"170056822142452237301","create_time":1514877424,"pay_status":1}
     */

    private String code;
    private String msg;
    private ResponseBean response;

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

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * uid : 722
         * money : 1
         * order_sn : 170056822142452237301
         * create_time : 1514877424
         * pay_status : 1
         */

        private String uid;
        private String money;
        private String order_sn;
        private String create_time;
        private String pay_status;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }
    }
}
