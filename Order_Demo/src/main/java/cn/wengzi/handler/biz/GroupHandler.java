package cn.wengzi.handler.biz;

import cn.wengzi.util.OrderTypeEnum;
import cn.wengzi.annotation.OrderType;
import cn.wengzi.handler.AbstractHandler;
import cn.wengzi.model.OrderDTO;
import org.springframework.stereotype.Component;

/**
 * 处理团购订单处理器
 */
@Component
@OrderType(OrderTypeEnum.GROUP)
public class GroupHandler extends AbstractHandler {

    @Override
    public String handle(OrderDTO dto) {
        return "处理团购订单";
    }
}
