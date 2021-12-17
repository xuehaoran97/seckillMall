package com.xxxx.seckillmall.vo;

import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
这里我所有的手机号都放行了
 */
@Slf4j
public class ValidatorUtil {

    private static final Pattern mobile_pattern =  Pattern.compile("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$");

    public static boolean isMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(mobile);

        //return matcher.matches();
        return true;
    }
}
