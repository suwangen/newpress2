package com.shensou.newpress.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/08/25 0025.
 */

public class RongyuGson {


    /**
     * code : 200
     * msg : 文章列表
     * response : {"Article":[{"id":61,"title1":"列表标题一","title2":"列表标题二","title3":"列表标题三","currency":1000,"create_time":"2017-12-11 14:47:02","thum":1347,"img":"http://moyin.plwx.com/uploads/picture/2017-12-25/5a40904abbdeb.png","status":-1}],"Stringegral":500000}
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
         * Article : [{"id":61,"title1":"列表标题一","title2":"列表标题二","title3":"列表标题三","currency":1000,"create_time":"2017-12-11 14:47:02","thum":1347,"img":"http://moyin.plwx.com/uploads/picture/2017-12-25/5a40904abbdeb.png","status":-1}]
         * Stringegral : 500000
         */

        private String integral;
        private List<ArticleBean> Article;

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public List<ArticleBean> getArticle() {
            return Article;
        }

        public void setArticle(List<ArticleBean> Article) {
            this.Article = Article;
        }

        public static class ArticleBean {
            /**
             * id : 61
             * title1 : 列表标题一
             * title2 : 列表标题二
             * title3 : 列表标题三
             * currency : 1000
             * create_time : 2017-12-11 14:47:02
             * thum : 1347
             * img : http://moyin.plwx.com/uploads/picture/2017-12-25/5a40904abbdeb.png
             * status : -1
             */

            private String id;
            private String title1;
            private String title2;
            private String title3;
            private String currency;
            private String create_time;
            private String thum;
            private String img;
            private String status;
            private String is_collect;

            public String getIs_collect() {
                return is_collect;
            }

            public void setIs_collect(String is_collect) {
                this.is_collect = is_collect;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle1() {
                return title1;
            }

            public void setTitle1(String title1) {
                this.title1 = title1;
            }

            public String getTitle2() {
                return title2;
            }

            public void setTitle2(String title2) {
                this.title2 = title2;
            }

            public String getTitle3() {
                return title3;
            }

            public void setTitle3(String title3) {
                this.title3 = title3;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getThum() {
                return thum;
            }

            public void setThum(String thum) {
                this.thum = thum;
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
}
