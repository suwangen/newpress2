package com.shensou.newpress.bean;

/**
 * Created by Administrator on 2016/5/3 0003.
 */
public class UpdateGson extends BaseGson {

    private UpdateDetail response;

    public UpdateDetail getData() {
        return response;
    }

    public void setData(UpdateDetail data) {
        this.response = data;
    }

    public class UpdateDetail{

        /**
         * id : 1
         * name : v1.00.12
         * path : http://f.xunyizhushou.com//Uploads/Products/2016-04-27/572070dd04a38.apk
         */

        private String id;
        private String name;
        private String path;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        private String content;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPath() {
            return path;
        }
    }
}
