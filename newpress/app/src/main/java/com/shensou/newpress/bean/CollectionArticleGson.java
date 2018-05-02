package com.shensou.newpress.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/08/22 0022.
 */

public class CollectionArticleGson {


    /**
     * code : 200
     * msg : 收藏列表
     * response : [{"id":3,"uid":722,"article_id":67,"create_time":"1970-01-01","cate_id":5,"title":"国语"}]
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
         * id : 3
         * uid : 722
         * article_id : 67
         * create_time : 1970-01-01
         * cate_id : 5
         * title : 国语
         */

        private String id;
        private String uid;
        private String article_id;
        private String create_time;
        private String cate_id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCate_id() {
            return cate_id;
        }

        public void setCate_id(String cate_id) {
            this.cate_id = cate_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
