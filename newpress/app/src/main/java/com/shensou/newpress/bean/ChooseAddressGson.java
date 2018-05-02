package com.shensou.newpress.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class ChooseAddressGson extends BaseGson{




    /**
     * area_id : 110000
     * title : 北京市
     * city_list : [{"area_id":"110100","title":"北京市","county_list":[{"area_id":"110101","title":"东城区"},{"area_id":"110102","title":"西城区"},{"area_id":"110103","title":"崇文区"},{"area_id":"110104","title":"宣武区"},{"area_id":"110105","title":"朝阳区"},{"area_id":"110106","title":"丰台区"},{"area_id":"110107","title":"石景山区"},{"area_id":"110108","title":"海淀区"},{"area_id":"110109","title":"门头沟区"},{"area_id":"110111","title":"房山区"},{"area_id":"110112","title":"通州区"},{"area_id":"110113","title":"顺义区"},{"area_id":"110114","title":"昌平区"},{"area_id":"110115","title":"大兴区"},{"area_id":"110116","title":"怀柔区"},{"area_id":"110117","title":"平谷区"}]}]
     */

    private List<ResponseEntity> response;



    public void setResponse(List<ResponseEntity> response) {
        this.response = response;
    }



    public List<ResponseEntity> getResponse() {
        return response;
    }

    public static class ResponseEntity {
        private String area_id;
        private String title;
        /**
         * area_id : 110100
         * title : 北京市
         * county_list : [{"area_id":"110101","title":"东城区"},{"area_id":"110102","title":"西城区"},{"area_id":"110103","title":"崇文区"},{"area_id":"110104","title":"宣武区"},{"area_id":"110105","title":"朝阳区"},{"area_id":"110106","title":"丰台区"},{"area_id":"110107","title":"石景山区"},{"area_id":"110108","title":"海淀区"},{"area_id":"110109","title":"门头沟区"},{"area_id":"110111","title":"房山区"},{"area_id":"110112","title":"通州区"},{"area_id":"110113","title":"顺义区"},{"area_id":"110114","title":"昌平区"},{"area_id":"110115","title":"大兴区"},{"area_id":"110116","title":"怀柔区"},{"area_id":"110117","title":"平谷区"}]
         */

        private List<CityListEntity> city_list;

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCity_list(List<CityListEntity> city_list) {
            this.city_list = city_list;
        }

        public String getArea_id() {
            return area_id;
        }

        public String getTitle() {
            return title;
        }

        public List<CityListEntity> getCity_list() {
            return city_list;
        }



        public static class CityListEntity {
            private String area_id;
            private String title;
            /**
             * area_id : 110101
             * title : 东城区
             */

            private List<CountyListEntity> county_list;

            public void setArea_id(String area_id) {
                this.area_id = area_id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setCounty_list(List<CountyListEntity> county_list) {
                this.county_list = county_list;
            }

            public String getArea_id() {
                return area_id;
            }

            public String getTitle() {
                return title;
            }

            public List<CountyListEntity> getCounty_list() {
                return county_list;
            }

            public static class CountyListEntity {
                private String area_id;
                private String title;

                public void setArea_id(String area_id) {
                    this.area_id = area_id;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getArea_id() {
                    return area_id;
                }

                public String getTitle() {
                    return title;
                }
            }
        }
    }
}
