package com.shensou.newpress.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/17 0017.
 */
public class NewsGson {


    /**
     * message : 视频列表
     * code : 200
     * response : [{"id":"3","watch_time":null,"video_path":"/uploads/picture/2017-05-31/592e565f0c511.png","path":"/uploads/picture/2017-05-31/592e565f0c511.png"}]
     * request : {"p":"0"}
     */

    private String message;
    private int code;
    private List<ResponseBean> response;

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


    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * id : 3
         * watch_time : null
         * video_path : /uploads/picture/2017-05-31/592e565f0c511.png
         * path : /uploads/picture/2017-05-31/592e565f0c511.png
         */

        private String id;
        private String name;
        private String t;
        private Object watch_time;
        private String video_path;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String path;

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getWatch_time() {
            return watch_time;
        }

        public void setWatch_time(Object watch_time) {
            this.watch_time = watch_time;
        }

        public String getVideo_path() {
            return video_path;
        }

        public void setVideo_path(String video_path) {
            this.video_path = video_path;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
