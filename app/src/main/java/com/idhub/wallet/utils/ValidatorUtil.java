package com.idhub.wallet.utils;

import java.util.regex.Pattern;

public class ValidatorUtil {
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }
    public static boolean isPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        return true;
    }

    //    /**
//     * 验证中国手机号
//     * @param phone
//     * @return
//     */
//    public static boolean verifyPhoneNumber(String phone) {
//        String regex = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
//        if (phone.length() != 11) {
//            ToastUtils.showShortToast("手机号长度应为11位数");
//            return false;
//        } else {
//            Pattern p = Pattern.compile(regex);
//            Matcher m = p.matcher(phone);
//            boolean isMatch = m.matches();
//            if (!isMatch) {
//                ToastUtils.showShortToast("请填入正确的手机号");
//            }
//            return isMatch;
//        }
//    }
}
