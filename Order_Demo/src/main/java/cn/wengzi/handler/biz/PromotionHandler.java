package cn.wengzi.handler.biz;

import cn.wengzi.util.OrderTypeEnum;
import cn.wengzi.annotation.OrderType;
import cn.wengzi.handler.AbstractHandler;
import cn.wengzi.model.OrderDTO;
import org.springframework.stereotype.Component;

/**
 * 促销订单处理器
 */
@Component
@OrderType(OrderTypeEnum.SALES)
public class PromotionHandler extends AbstractHandler {
    @Override
    public String handle(OrderDTO dto) {
        return "处理促销订单";
    }
}
