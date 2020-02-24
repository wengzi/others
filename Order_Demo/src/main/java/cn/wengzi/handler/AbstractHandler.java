package cn.wengzi.handler;

import cn.wengzi.model.OrderDTO;

public abstract class AbstractHandler {
    abstract public String handle(OrderDTO dto);
}
