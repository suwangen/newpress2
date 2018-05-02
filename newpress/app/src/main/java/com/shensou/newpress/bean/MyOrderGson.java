package com.shensou.newpress.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class MyOrderGson {

    /**
     * code : 200
     * msg : 订单列表
     * response : [{"order_id":506,"order_sn":"939056725135964756501","pay_sn":"390567251359647565","store_id":0,"store_name":null,"buyer_id":565,"buyer_message":"","address_id":14,"create_time":1513907359,"pay_status":1,"payment_time":0,"delay_type":1,"finnshed_time":0,"goods_amount":"270.00","order_amount":"270.00","shipping_fee":"0.00","voucher_price":"0.00","evaluation_state":0,"order_state":10,"order_changetype_state":0,"refund_state":0,"lock_state":0,"delete_state":0,"refund_amount":"0.00","delay_time":0,"order_from":2,"order_message":null,"shipping_code":"","epress_name":null,"goodstype_poString":0,"rebate":30,"orderAddress":{"id":14,"uid":565,"name":"苏万根","tel":"13235007151","area_id":350212,"address":"梧侣城市天骄","create_time":1513591139,"update_time":1513731726,"status":2,"areatitle":"福建省厦门市同安区"},"orderGoods":[{"id":575,"order_id":506,"goods_id":34,"option_id":469,"option_name":"白色+170","goods_name":"测试产品请勿下单2","goods_price":"180.00","goods_num":2,"rebate":"20","goods_image":"","goods_pay_price":"90.00","store_id":0,"goods_type":"1","promotions_id":0,"commis_rate":0,"gc_id":0,"store_name":null},{"id":576,"order_id":506,"goods_id":33,"option_id":0,"option_name":"","goods_name":"测试产品请勿下单","goods_price":"90.00","goods_num":1,"rebate":"10","goods_image":"1594","goods_pay_price":"90.00","store_id":0,"goods_type":"1","promotions_id":0,"commis_rate":0,"gc_id":0,"store_name":null}]},{"order_id":507,"order_sn":"939056725135964756501","pay_sn":"390567251359647565","store_id":1013,"store_name":"妍馨广告-同安","buyer_id":565,"buyer_message":"","address_id":14,"create_time":1513907359,"pay_status":1,"payment_time":0,"delay_type":1,"finnshed_time":0,"goods_amount":"5000.00","order_amount":"5000.00","shipping_fee":"0.00","voucher_price":"0.00","evaluation_state":0,"order_state":10,"order_changetype_state":0,"refund_state":0,"lock_state":0,"delete_state":0,"refund_amount":"0.00","delay_time":0,"order_from":2,"order_message":null,"shipping_code":"","epress_name":null,"goodstype_poString":0,"rebate":2,"orderAddress":{"id":14,"uid":565,"name":"苏万根","tel":"13235007151","area_id":350212,"address":"梧侣城市天骄","create_time":1513591139,"update_time":1513731726,"status":2,"areatitle":"福建省厦门市同安区"},"orderGoods":[{"id":577,"order_id":507,"goods_id":16,"option_id":0,"option_name":"","goods_name":"美女老师1","goods_price":"5000.00","goods_num":1,"rebate":"2","goods_image":"1869","goods_pay_price":"5000.00","store_id":1013,"goods_type":"1","promotions_id":0,"commis_rate":0,"gc_id":0,"store_name":"妍馨广告-同安"}]}]
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
         * order_id : 506
         * order_sn : 939056725135964756501
         * pay_sn : 390567251359647565
         * store_id : 0
         * store_name : null
         * buyer_id : 565
         * buyer_message : 
         * address_id : 14
         * create_time : 1513907359
         * pay_status : 1
         * payment_time : 0
         * delay_type : 1
         * finnshed_time : 0
         * goods_amount : 270.00
         * order_amount : 270.00
         * shipping_fee : 0.00
         * voucher_price : 0.00
         * evaluation_state : 0
         * order_state : 10
         * order_changetype_state : 0
         * refund_state : 0
         * lock_state : 0
         * delete_state : 0
         * refund_amount : 0.00
         * delay_time : 0
         * order_from : 2
         * order_message : null
         * shipping_code : 
         * epress_name : null
         * goodstype_poString : 0
         * rebate : 30
         * orderAddress : {"id":14,"uid":565,"name":"苏万根","tel":"13235007151","area_id":350212,"address":"梧侣城市天骄","create_time":1513591139,"update_time":1513731726,"status":2,"areatitle":"福建省厦门市同安区"}
         * orderGoods : [{"id":575,"order_id":506,"goods_id":34,"option_id":469,"option_name":"白色+170","goods_name":"测试产品请勿下单2","goods_price":"180.00","goods_num":2,"rebate":"20","goods_image":"","goods_pay_price":"90.00","store_id":0,"goods_type":"1","promotions_id":0,"commis_rate":0,"gc_id":0,"store_name":null},{"id":576,"order_id":506,"goods_id":33,"option_id":0,"option_name":"","goods_name":"测试产品请勿下单","goods_price":"90.00","goods_num":1,"rebate":"10","goods_image":"1594","goods_pay_price":"90.00","store_id":0,"goods_type":"1","promotions_id":0,"commis_rate":0,"gc_id":0,"store_name":null}]
         */

        private String order_id;
        private String order_sn;
        private String pay_sn;
        private String store_id;
        private String store_name;
        private String buyer_id;
        private String buyer_message;
        private String address_id;
        private String create_time;
        private String pay_status;
        private String payment_time;
        private String delay_type;
        private String finnshed_time;
        private String goods_amount;
        private String order_amount;
        private String shipping_fee;
        private String voucher_price;
        private String evaluation_state;
        private String order_state;
        private String order_type;//订单类型 1商品 2 预约
        private String is_break;//1正常 2爽约

        public String getIs_break() {
            return is_break;
        }

        public void setIs_break(String is_break) {
            this.is_break = is_break;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        private String order_changetype_state;
        private String lock_state;
        private String delete_state;
        private String refund_amount;
        private String delay_time;
        private String order_from;
        private String order_message;
        private String shipping_code;
        private String epress_name;
        private String goodstype_poString;
        private String rebate;
        private String refund_state;// 0是退款,1是退货退款
        private String refund_deal;//1 处理中 2 同意退款 3 拒绝退款
        public String getRefund_deal() {
            return refund_deal;
        }

        public void setRefund_deal(String refund_deal) {
            this.refund_deal = refund_deal;
        }


        private List<OrderGoodsBean> orderGoods;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getPay_sn() {
            return pay_sn;
        }

        public void setPay_sn(String pay_sn) {
            this.pay_sn = pay_sn;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getBuyer_id() {
            return buyer_id;
        }

        public void setBuyer_id(String buyer_id) {
            this.buyer_id = buyer_id;
        }

        public String getBuyer_message() {
            return buyer_message;
        }

        public void setBuyer_message(String buyer_message) {
            this.buyer_message = buyer_message;
        }

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getPayment_time() {
            return payment_time;
        }

        public void setPayment_time(String payment_time) {
            this.payment_time = payment_time;
        }

        public String getDelay_type() {
            return delay_type;
        }

        public void setDelay_type(String delay_type) {
            this.delay_type = delay_type;
        }

        public String getFinnshed_time() {
            return finnshed_time;
        }

        public void setFinnshed_time(String finnshed_time) {
            this.finnshed_time = finnshed_time;
        }

        public String getGoods_amount() {
            return goods_amount;
        }

        public void setGoods_amount(String goods_amount) {
            this.goods_amount = goods_amount;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public String getShipping_fee() {
            return shipping_fee;
        }

        public void setShipping_fee(String shipping_fee) {
            this.shipping_fee = shipping_fee;
        }

        public String getVoucher_price() {
            return voucher_price;
        }

        public void setVoucher_price(String voucher_price) {
            this.voucher_price = voucher_price;
        }

        public String getEvaluation_state() {
            return evaluation_state;
        }

        public void setEvaluation_state(String evaluation_state) {
            this.evaluation_state = evaluation_state;
        }

        public String getOrder_state() {
            return order_state;
        }

        public void setOrder_state(String order_state) {
            this.order_state = order_state;
        }

        public String getOrder_changetype_state() {
            return order_changetype_state;
        }

        public void setOrder_changetype_state(String order_changetype_state) {
            this.order_changetype_state = order_changetype_state;
        }

        public String getRefund_state() {
            return refund_state;
        }

        public void setRefund_state(String refund_state) {
            this.refund_state = refund_state;
        }

        public String getLock_state() {
            return lock_state;
        }

        public void setLock_state(String lock_state) {
            this.lock_state = lock_state;
        }

        public String getDelete_state() {
            return delete_state;
        }

        public void setDelete_state(String delete_state) {
            this.delete_state = delete_state;
        }

        public String getRefund_amount() {
            return refund_amount;
        }

        public void setRefund_amount(String refund_amount) {
            this.refund_amount = refund_amount;
        }

        public String getDelay_time() {
            return delay_time;
        }

        public void setDelay_time(String delay_time) {
            this.delay_time = delay_time;
        }

        public String getOrder_from() {
            return order_from;
        }

        public void setOrder_from(String order_from) {
            this.order_from = order_from;
        }

        public String getOrder_message() {
            return order_message;
        }

        public void setOrder_message(String order_message) {
            this.order_message = order_message;
        }

        public String getShipping_code() {
            return shipping_code;
        }

        public void setShipping_code(String shipping_code) {
            this.shipping_code = shipping_code;
        }

        public String getEpress_name() {
            return epress_name;
        }

        public void setEpress_name(String epress_name) {
            this.epress_name = epress_name;
        }

        public String getGoodstype_poString() {
            return goodstype_poString;
        }

        public void setGoodstype_poString(String goodstype_poString) {
            this.goodstype_poString = goodstype_poString;
        }

        public String getRebate() {
            return rebate;
        }

        public void setRebate(String rebate) {
            this.rebate = rebate;
        }


        public List<OrderGoodsBean> getOrderGoods() {
            return orderGoods;
        }

        public void setOrderGoods(List<OrderGoodsBean> orderGoods) {
            this.orderGoods = orderGoods;
        }


        public static class OrderGoodsBean {
            /**
             * id : 575
             * order_id : 506
             * goods_id : 34
             * option_id : 469
             * option_name : 白色+170
             * goods_name : 测试产品请勿下单2
             * goods_price : 180.00
             * goods_num : 2
             * rebate : 20
             * goods_image : 
             * goods_pay_price : 90.00
             * store_id : 0
             * goods_type : 1
             * promotions_id : 0
             * commis_rate : 0
             * gc_id : 0
             * store_name : null
             */

            private String id;
            private String order_id;
            private String goods_id;
            private String option_id;
            private String option_name;
            private String goods_name;
            private String goods_price;
            private String goods_num;
            private String rebate;
            private String goods_image;
            private String goods_pay_price;
            private String store_id;
            private String goods_type;
            private String promotions_id;
            private String commis_rate;
            private String gc_id;
            private String store_name;
            private String is_show;//1上架 0下架

            public String getIs_show() {
                return is_show;
            }

            public void setIs_show(String is_show) {
                this.is_show = is_show;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getOption_id() {
                return option_id;
            }

            public void setOption_id(String option_id) {
                this.option_id = option_id;
            }

            public String getOption_name() {
                return option_name;
            }

            public void setOption_name(String option_name) {
                this.option_name = option_name;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(String goods_num) {
                this.goods_num = goods_num;
            }

            public String getRebate() {
                return rebate;
            }

            public void setRebate(String rebate) {
                this.rebate = rebate;
            }

            public String getGoods_image() {
                return goods_image;
            }

            public void setGoods_image(String goods_image) {
                this.goods_image = goods_image;
            }

            public String getGoods_pay_price() {
                return goods_pay_price;
            }

            public void setGoods_pay_price(String goods_pay_price) {
                this.goods_pay_price = goods_pay_price;
            }

            public String getStore_id() {
                return store_id;
            }

            public void setStore_id(String store_id) {
                this.store_id = store_id;
            }

            public String getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(String goods_type) {
                this.goods_type = goods_type;
            }

            public String getPromotions_id() {
                return promotions_id;
            }

            public void setPromotions_id(String promotions_id) {
                this.promotions_id = promotions_id;
            }

            public String getCommis_rate() {
                return commis_rate;
            }

            public void setCommis_rate(String commis_rate) {
                this.commis_rate = commis_rate;
            }

            public String getGc_id() {
                return gc_id;
            }

            public void setGc_id(String gc_id) {
                this.gc_id = gc_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }
        }
    }
}
