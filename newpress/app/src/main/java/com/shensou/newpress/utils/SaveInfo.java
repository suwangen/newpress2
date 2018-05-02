package com.shensou.newpress.utils;

import android.content.Context;

import com.shensou.newpress.gobal.UserInfoXML;
import com.shensou.newpress.bean.UserInfoGson;


/**
 * Created by Administrator on 2016/3/3 0003.
 */
public class SaveInfo {



    /**
     * 保存用户信息
     */
    public static void safeUserInfo(Context context, UserInfoGson detail) {
        UserInfoXML mUserInfoXML = UserInfoXML.getInstance(context);
        mUserInfoXML.setusername(detail.getUsername());
        mUserInfoXML.setUser_avatar(detail.getUserpic());
    }

}
