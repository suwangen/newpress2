package com.shensou.newpress.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/08/23 0023.
 */

public class LifeGson {


    /**
     * message : 生活部
     * code : 200
     * response : {"dment":[{"id":"1","dment":"自律部 ---- 课间操检查","create_time":"1503036451"},{"id":"2","dment":"生活部 ---- 班级卫生检查","create_time":"1503036466"},{"id":"3","dment":"宿管部 ---- 宿舍卫生检查","create_time":"1503036476"},{"id":"5","dment":"自律部 ---- 晚自习检查","create_time":"1503044026"},{"id":"6","dment":"自律部 ---- 眼保健操检查","create_time":"1503044043"}],"class_cate":[{"id":"42","title":"电子技术及应用","pid":"0","sort":"0","list_row":"10","meta_title":"","description":"","create_time":"1503046503","update_time":"1503046503","status":"1","icon":"0","groups":""}],"year":{"id":"2","start_time":null,"end_time":null,"year":"2010年9月1日 -- 2013年7月1日 ","create_time":"1502975379"},"weeks":{"id":"117","weeks_num":"2","dates":"2017-08-23","year_id":"2","create_time":"1503045074","week":"星期三"}}
     * request : {"type":"1"}
     */

    private String message;
    private int code;
    private ResponseBean response;
    private RequestBean request;

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

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public RequestBean getRequest() {
        return request;
    }

    public void setRequest(RequestBean request) {
        this.request = request;
    }

    public static class ResponseBean {
        /**
         * dment : [{"id":"1","dment":"自律部 ---- 课间操检查","create_time":"1503036451"},{"id":"2","dment":"生活部 ---- 班级卫生检查","create_time":"1503036466"},{"id":"3","dment":"宿管部 ---- 宿舍卫生检查","create_time":"1503036476"},{"id":"5","dment":"自律部 ---- 晚自习检查","create_time":"1503044026"},{"id":"6","dment":"自律部 ---- 眼保健操检查","create_time":"1503044043"}]
         * class_cate : [{"id":"42","title":"电子技术及应用","pid":"0","sort":"0","list_row":"10","meta_title":"","description":"","create_time":"1503046503","update_time":"1503046503","status":"1","icon":"0","groups":""}]
         * year : {"id":"2","start_time":null,"end_time":null,"year":"2010年9月1日 -- 2013年7月1日 ","create_time":"1502975379"}
         * weeks : {"id":"117","weeks_num":"2","dates":"2017-08-23","year_id":"2","create_time":"1503045074","week":"星期三"}
         */

        private YearBean year;
        private WeeksBean weeks;
        private List<DmentBean> dment;
        private List<ClassCateBean> class_cate;

        public YearBean getYear() {
            return year;
        }

        public void setYear(YearBean year) {
            this.year = year;
        }

        public WeeksBean getWeeks() {
            return weeks;
        }

        public void setWeeks(WeeksBean weeks) {
            this.weeks = weeks;
        }

        public List<DmentBean> getDment() {
            return dment;
        }

        public void setDment(List<DmentBean> dment) {
            this.dment = dment;
        }

        public List<ClassCateBean> getClass_cate() {
            return class_cate;
        }

        public void setClass_cate(List<ClassCateBean> class_cate) {
            this.class_cate = class_cate;
        }

        public static class YearBean {
            /**
             * id : 2
             * start_time : null
             * end_time : null
             * year : 2010年9月1日 -- 2013年7月1日
             * create_time : 1502975379
             */

            private String id;
            private Object start_time;
            private Object end_time;
            private String year;
            private String create_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Object getStart_time() {
                return start_time;
            }

            public void setStart_time(Object start_time) {
                this.start_time = start_time;
            }

            public Object getEnd_time() {
                return end_time;
            }

            public void setEnd_time(Object end_time) {
                this.end_time = end_time;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }

        public static class WeeksBean {
            /**
             * id : 117
             * weeks_num : 2
             * dates : 2017-08-23
             * year_id : 2
             * create_time : 1503045074
             * week : 星期三
             */

            private String id;
            private String weeks_num;
            private String dates;
            private String year_id;
            private String create_time;
            private String week;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getWeeks_num() {
                return weeks_num;
            }

            public void setWeeks_num(String weeks_num) {
                this.weeks_num = weeks_num;
            }

            public String getDates() {
                return dates;
            }

            public void setDates(String dates) {
                this.dates = dates;
            }

            public String getYear_id() {
                return year_id;
            }

            public void setYear_id(String year_id) {
                this.year_id = year_id;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }
        }

        public static class DmentBean {
            /**
             * id : 1
             * dment : 自律部 ---- 课间操检查
             * create_time : 1503036451
             */

            private String id;
            private String dment;
            private String create_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDment() {
                return dment;
            }

            public void setDment(String dment) {
                this.dment = dment;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }

        public static class ClassCateBean {
            /**
             * id : 42
             * title : 电子技术及应用
             * pid : 0
             * sort : 0
             * list_row : 10
             * meta_title :
             * description :
             * create_time : 1503046503
             * update_time : 1503046503
             * status : 1
             * icon : 0
             * groups :
             */

            private String id;
            private String title;
            private String pid;
            private String sort;
            private String list_row;
            private String meta_title;
            private String description;
            private String create_time;
            private String update_time;
            private String status;
            private String icon;
            private String groups;

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

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getList_row() {
                return list_row;
            }

            public void setList_row(String list_row) {
                this.list_row = list_row;
            }

            public String getMeta_title() {
                return meta_title;
            }

            public void setMeta_title(String meta_title) {
                this.meta_title = meta_title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getGroups() {
                return groups;
            }

            public void setGroups(String groups) {
                this.groups = groups;
            }
        }
    }

    public static class RequestBean {
        /**
         * type : 1
         */

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
