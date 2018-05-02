package com.shensou.newpress.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/06 0006.
 */

public class VisitPeopleGson {

    /**
     * code : 200
     * msg : 访客信息
     * response : [{"name":"苏万根","nickname":"gen","logo":"http://wx.qlogo.cn/mmhead/I1YzhXxW8YCSDJeW1PhnbnwCgm9UPcKFbPr9zXu6NyJJw03ATQiakRQ/0","area_id":110101,"create_time":12,"areatitle":"北京市北京市"},{"name":"苏万根","nickname":"gen","logo":"http://wx.qlogo.cn/mmhead/I1YzhXxW8YCSDJeW1PhnbnwCgm9UPcKFbPr9zXu6NyJJw03ATQiakRQ/0","area_id":110101,"create_time":1509950173,"areatitle":"北京市北京市"}]
     * request : {"uid":"644"}
     * time : 1509959157
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
         * name : 苏万根
         * nickname : gen
         * logo : http://wx.qlogo.cn/mmhead/I1YzhXxW8YCSDJeW1PhnbnwCgm9UPcKFbPr9zXu6NyJJw03ATQiakRQ/0
         * area_id : 110101
         * create_time : 12
         * areatitle : 北京市北京市
         */

        private String name;
        private String nickname;
        private String logo;
        private String area_id;
        private String update_time;
        private String areatitle;
        private String uid;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getAreatitle() {
            return areatitle;
        }

        public void setAreatitle(String areatitle) {
            this.areatitle = areatitle;
        }
    }
}
