package com.shensou.newpress.bean;

import java.util.List;

/**
 * 娱乐下的二级分类
 */

public class YuleTwoClassifyGson {

    /**
     * code : 200
     * msg : 二级分类
     * response : [{"id":22,"title":"秘籍典藏","icon":"http://moyin.plwx.com/uploads/pic5/5a4069773d5c3.png"},{"id":23,"title":"版本水晶","icon":"http://moyin.plwx.com/uploads/pictur961c7ab4.png"}]
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
         * id : 22
         * title : 秘籍典藏
         * icon : http://moyin.plwx.com/uploads/pic5/5a4069773d5c3.png
         */

        private String id;
        private String title;
        private String icon;

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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
