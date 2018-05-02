package com.shensou.newpress.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/01/08 0008.
 */

public class BoxGson {

    /**
     * code : 200
     * msg : 宝箱列表
     * response : [{"id":1,"title":"宝箱一","content":"<p>宝箱一<\/p>","sum":101,"create_time":"2018-01-02 12:42:13","img":"http://moyin.plwx.com/uploads/picture/2017-12-25/5a40904abbdeb.png","status":-1},{"id":2,"title":"宝箱二","content":"<p>宝箱二<\/p>","sum":50,"create_time":"2018-01-02 11:40:31","img":"http://moyin.plwx.com/uploads/picture/2017-12-25/5a409ed264cec.png","status":-1}]
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
         * title : 宝箱一
         * content : <p>宝箱一</p>
         * sum : 101
         * create_time : 2018-01-02 12:42:13
         * img : http://moyin.plwx.com/uploads/picture/2017-12-25/5a40904abbdeb.png
         * status : -1
         */

        private String id;
        private String title;
        private String content;
        private String sum;
        private String create_time;
        private String img;
        private String status;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
