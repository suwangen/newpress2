package com.shensou.newpress.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 专人预约等
 */

public class YuyueSpecialPersonGson {


    /**
     * code : 200
     * msg : 获取成功
     * response : {"class":[{"logo":"","gc_id":3,"gc_name":"老师"}],"brand":[{"brand_id":373,"brand_name":"品牌","brand_pic":"http://yesno1.plwx.com/u1e7d467949a.png"}],"active_type":[{"id":1,"title":"逛逛","sort":1,"create_time":1512373237,"update_time":1512379933},{"id":2,"title":"抢购","sort":2,"create_time":1512373253,"update_time":1512373269},{"id":3,"title":"出清","sort":3,"create_time":1512373279,"update_time":1512373279},{"id":4,"title":"热卖","sort":4,"create_time":1512373295,"update_time":1512379449}]}
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
        @SerializedName("class")
        private List<ClassBean> classX;
        private List<BrandBean> brand;
        private List<ActiveTypeBean> active_type;

        public List<ClassBean> getClassX() {
            return classX;
        }

        public void setClassX(List<ClassBean> classX) {
            this.classX = classX;
        }

        public List<BrandBean> getBrand() {
            return brand;
        }

        public void setBrand(List<BrandBean> brand) {
            this.brand = brand;
        }

        public List<ActiveTypeBean> getActive_type() {
            return active_type;
        }

        public void setActive_type(List<ActiveTypeBean> active_type) {
            this.active_type = active_type;
        }

        public static class ClassBean {
            /**
             * logo : 
             * gc_id : 3
             * gc_name : 老师
             */

            private String logo;
            private String gc_id;
            private String gc_name;

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getGc_id() {
                return gc_id;
            }

            public void setGc_id(String gc_id) {
                this.gc_id = gc_id;
            }

            public String getGc_name() {
                return gc_name;
            }

            public void setGc_name(String gc_name) {
                this.gc_name = gc_name;
            }
        }

        public static class BrandBean {
            /**
             * brand_id : 373
             * brand_name : 品牌
             * brand_pic : http://yesno1.plwx.com/u1e7d467949a.png
             */

            private String brand_id;
            private String brand_name;
            private String brand_pic;

            public String getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(String brand_id) {
                this.brand_id = brand_id;
            }

            public String getBrand_name() {
                return brand_name;
            }

            public void setBrand_name(String brand_name) {
                this.brand_name = brand_name;
            }

            public String getBrand_pic() {
                return brand_pic;
            }

            public void setBrand_pic(String brand_pic) {
                this.brand_pic = brand_pic;
            }
        }

        public static class ActiveTypeBean {
            /**
             * id : 1
             * title : 逛逛
             * sort : 1
             * create_time : 1512373237
             * update_time : 1512379933
             */

            private String id;
            private String title;
            private String sort;
            private String create_time;
            private String update_time;

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

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }
        }
    }
}
