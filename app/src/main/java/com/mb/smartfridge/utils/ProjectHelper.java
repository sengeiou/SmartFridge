package com.mb.smartfridge.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.mb.smartfridge.entity.ErrorResp;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectHelper {
    /**
     * 防止控件被连续点击
     * @param view
     */
    public static void disableViewDoubleClick(final View view) {
        if(view==null) {
            return;
        }
        view.setEnabled(false);
        view.postDelayed(new Runnable() {

            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, 3000);
    }

    /***
     * 判断 String 是否是 int
     *
     * @param input
     * @return
     */
    public static boolean isInteger(String input){
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }

    /***
     * 判断 String 是否是 int
     *
     * @param input
     * @return
     */
    public static boolean isHttp(String input){
        Matcher mer = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\/])+$").matcher(input);
        return mer.find();
    }

    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }

    /**
     * 手机验证
     * @param telNum
     * @return
     */
    public static boolean isMobiPhoneNum(String telNum) {
        String regex = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

    /**
     * 电话号码验证
     * @author ：shijing
     * 2016年12月5日下午4:34:21
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isPhone(final String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 密码格式验证
     * @param pwd
     * @return
     */
    public static boolean isPwdValid(String pwd) {
        String regex = "^[a-zA-Z0-9]{6,30}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    /**
     * 手机验证
     * @param idCard
     * @return
     */
    public static boolean isIdcard(String idCard) {
        String regex = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(idCard);
        return m.matches();
    }

    /**
     * 用Intent打开url(即处理外部链接地址)
     *
     * @param context
     * @param url
     */
    public static void openUrlWithIntent(Context context, String url) {
        if (context == null || TextUtils.isEmpty(url)) {
            return;
        }
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void showErrorMessage(String message){
        String errorMessage = "服务器内部错误";
        if (!TextUtils.isEmpty(message)){
            ErrorResp errorResp = JsonHelper.fromJson(message, ErrorResp.class);
            if (errorResp!=null){
                int errorCode = errorResp.getCode();
                if (errorCode == 200){
                    errorMessage = "没有提供用户名，或者用户名为空";
                }else if (errorCode == 201){
                    errorMessage = "没有提供密码，或者密码为空";
                }else if (errorCode == 202){
                    errorMessage = "用户名已经被占用";
                }else if (errorCode == 203){
                    errorMessage = "电子邮箱地址已经被占用";
                }else if (errorCode == 210){
                    errorMessage = "用户名和密码不匹配";
                }else if (errorCode == 211){
                    errorMessage = "找不到用户";
                }else if (errorCode == 214){
                    errorMessage = "手机号码已经被注册";
                }else if (errorCode == 215){
                    errorMessage = "未验证的手机号码";
                }else if (errorCode == 217){
                    errorMessage = "无效的用户名，不允许空白用户名";
                }else if (errorCode == 218){
                    errorMessage = "无效的密码，不允许空白密码";
                }else if (errorCode == 601){
                    errorMessage = "发送短信过于频繁";
                }else if (errorCode == 602){
                    errorMessage = "发送短信或者语音验证码失败";
                }else if (errorCode == 603){
                    errorMessage = "验证码错误";
                }
            }
        }
        ToastHelper.showToast(errorMessage);

    }



}
