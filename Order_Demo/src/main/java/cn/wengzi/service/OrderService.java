package cn.wengzi.service;

import cn.wengzi.model.OrderDTO;

/**
 * @author leizzige
 */
public interface OrderService {

    /**
     * 根据订单类型同作出处理
     *
     * @param dto 订单实体
     * @return string
     */
    String handler(OrderDTO dto);
}
