package com.maxuan.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/******************************
 *
 * 码炫课堂技术交流Q群：963060292
 * 主讲：smart哥
 *
 ******************************/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    int seconds();
    int maxCount();
    boolean needLogin() default true;
}
