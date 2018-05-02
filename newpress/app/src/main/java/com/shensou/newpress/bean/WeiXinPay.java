package com.shensou.newpress.bean;

/**
 * Created by Administrator on 2016/12/16 0016.
 */
public class WeiXinPay {


    /**
     * code : 200
     * msg : 调起数据
     * response : {"return_code":"SUCCESS","return_msg":"OK","appid":"wx06e6cc4a4f03745f","mch_id":"1417082102","nonce_str":"Rk23OAttLnmn6ZBg","sign":"55F91546BD7B6463172B31430480A5FC","result_code":"SUCCESS","prepay_id":"wx2016121616275644f178a4a70765935503","trade_type":"APP"}
     * request : []
     * time : 1481876874
     */

    private int code;
    private String msg;
    /**
     * return_code : SUCCESS
     * return_msg : OK
     * appid : wx06e6cc4a4f03745f
     * mch_id : 1417082102
     * nonce_str : Rk23OAttLnmn6ZBg
     * sign : 55F91546BD7B6463172B31430480A5FC
     * result_code : SUCCESS
     * prepay_id : wx2016121616275644f178a4a70765935503
     * trade_type : APP
     */

    private WeiXinData response;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setResponse(WeiXinData response) {
        this.response = response;
    }



    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public WeiXinData getResponse() {
        return response;
    }


    public static class WeiXinData {



        private String appid;
        private String noncestr;
        private String partnerid;
        private String prepayid;
        private String timestamp;
        private String sign;

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }



        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getAppid() {
            return appid;
        }

        public String getNoncestr() {
            return noncestr;
        }



        public String getPartnerid() {
            return partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public String getSign() {
            return sign;
        }
    }
}
