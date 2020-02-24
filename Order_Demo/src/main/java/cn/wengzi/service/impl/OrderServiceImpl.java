package cn.wengzi.service.impl;

import cn.wengzi.handler.AbstractHandler;
import cn.wengzi.handler.HandlerContext;
import cn.wengzi.model.OrderDTO;
import cn.wengzi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private HandlerContext context;

    public String handler(OrderDTO dto) {
        AbstractHandler handler = context.getInstance(dto.getType());
        return handler.handle(dto);
    }
}
