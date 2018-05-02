package com.shensou.newpress.gobal;

/**
 * Created by Administrator on 2016/12/4 0004.
 */
public class URL {
    public final static String HOST="http://39.108.60.146";
    public final static String IP="http://39.108.60.146/e/wxapi/";
    public final static String IP2="http://api.no1.ybinu.cn/dist/#";

    public final static String IP_IMG="http://tjb.xmshensou.com/";
    //获取用户信息
    public final static String GET_USER_INFO=IP+"meminfoget.php";
    //登录
    public final static String POST_LOGIN=IP+"login.php";
    //登录
    public final static String POST_TREELOGIN=IP+"treeLogin.php";
    //注册
    public final static String POST_REGISTER=IP+"register.php";
    //登入页面邀请码
    public final static String POST_CODE=IP+"User/code";
    //注册短信发送接口
    public final static String GET_CODE=IP+"appinterface/sendsms";
    //获取忘记密码验证码
    public static final String GET_PWD_CODE=IP+"/Public/sendMobileCode";
    //获取忘记密码验证码
    public static final String GET_PWD_MOBILECODE=IP+"User/sendMobileFindCode";
    //验证手机号
    public final static String VERIFICATION_CODE=IP+"/Public/findForgetPassword";
    //重置密碼
    public final static String RESET_PWD=IP+"User/updateForgetPassword";
    //修改昵称
    public final static String CHANGE_NICK_NAME=IP+"/User/updateUserNickName";
    //修改签名
    public final static String CHANGE_NUMBER=IP+"User/CheckUser";
    public final static String GET_AREA=IP+"Public/getArea";
    //修改密码
    public final static String CHANGE_PWD=IP+"User/updateUserPassword";
    //添加反馈
    public final static String FEEDBACK =IP+"User/addSystemFeedback";
    //获取首页广告
    public final static String MAIN_BANNNER=IP+"appinterface/getbanner";
    //获取通知页面文章列表
    public final static String ARTICLE_GETNEWTARTICLE=IP+"Article/getNewTArticle";
    //退出登录
    public final static String LOGOUT=IP+"/Public/logout";
    //修改头像
    public final static String CHANGE_HEAD=IP+"Users/updateUserAvatar";
    //检测新版本
    public final static String CHECK_UPDATE=IP+"App/getVersion";
    //消息列表
    public final static String PUST_GET_WATCH=IP+"Pust/get_watch";
    //会员充值下拉选项
    public final static String USERS_GETTIMELENGTH=IP+"Users/GetTimeLength";
    //充值下单
    public final static String ORDER_XIANTAO=IP+"Order/XianTao";
    //会员充值下单接口
    public final static String ORDER_MEMBERPAY=IP+"Order/MemberPay";
    //微信充值
    public final static String WXPAY_GETINDEX=IP+"Wxpay/getIndex";
    //会员充值支付接口
    public final static String WXPAY_MEMBERPAY=IP+"Wxpay/MemberPay";

    //我的订单列表
    public final static String APPOINTMENT_ORDERLIST=IP+"appointment/orderList";
    //微信支付接口
    public final static String WXPAY_BUYCARD=IP+"Wxpay/buyCard";
    //电桩列表
    public final static String APPINTERFACE_GETPILE=IP+"appinterface/getpile";

    //h5链接
    //咨询列表
    public final static String HOME_TOP_ZIXUN_LIST="http://api.no1.ybinu.cn/dist/#/information";
    //课堂列表
    public final static String HOME_TOP_CLASS_LIST="http://api.no1.ybinu.cn/dist/#/index/classroom/classroom";
    //问答列表
    public final static String HOME_TOP_ANSWER_LIST="http://api.no1.ybinu.cn/dist/#/index/answer/answer";
    //转让列表
    public final static String HOME_TOP_TRANSFER_LIST="http://api.no1.ybinu.cn/dist/#/index/transfer/transferred";
    //竞标列表
    public final static String HOME_TOP_BIDDING_LIST="http://api.no1.ybinu.cn/dist/#/index/bidding/bidding";
    //招聘列表
    public final static String HOME_TOP_RECRUIT_LIST="http://api.no1.ybinu.cn/dist/#/index/recruit/recruit";
    //消息
    public final static String HOME_BOTTOM_NEWS_TAB="http://api.no1.ybinu.cn/dist/#/news";
    //圈子
    public final static String HOME_BOTTOM_CIRCLE_TAB="http://api.no1.ybinu.cn/dist/#/circle/circle";
    //资讯发布
    public final static String DISTRIBUTE_ZIXUN="http://api.no1.ybinu.cn/dist/#/post/add";
    //课堂发布
    public final static String DISTRIBUTE_CLASSROOM="http://api.no1.ybinu.cn/dist/#/index/classroom/classroomAdd";
    //提问问题
    public final static String DISTRIBUTE_ANSWER="http://api.no1.ybinu.cn/dist/#/index/answer/answerAdd";
    //出售网店
    public final static String DISTRIBUTE_TRANSFER="http://api.no1.ybinu.cn/dist/#/index/transfer/transferredDetail";
    //竞标发布
    public final static String DISTRIBUTE_BIDDING="http://api.no1.ybinu.cn/dist/#/index/bidding/biddingAdd";
    //职位发布
    public final static String DISTRIBUTE_RECRUIT="http://api.no1.ybinu.cn/dist/#/index/recruit/recruitAdd";

    //我的发布
    public final static String USER_RELEASE="http://api.no1.ybinu.cn/dist/#/user/release";
    //我的关注
    public final static String USER_FOLLOW="http://api.no1.ybinu.cn/dist/#/user/follow";
    //报价订单
    public final static String USER_OFFERORDER="http://api.no1.ybinu.cn/dist/#/user/offerOrder";
    //我的订单  买家
    public final static String USER_MJORDER="http://api.no1.ybinu.cn/dist/#/user/mjOrder";
    //实名认证
    public final static String USER_CERTIFICATION="http://api.no1.ybinu.cn/dist/#/user/certification";

}
