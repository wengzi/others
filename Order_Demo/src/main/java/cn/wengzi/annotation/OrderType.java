package cn.wengzi.annotation;

import cn.wengzi.util.OrderTypeEnum;

import java.lang.annotation.*;

/**
 * 自定义注解,订单类型
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OrderType {

    OrderTypeEnum value();
}
