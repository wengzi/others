package cn.wengzi.controller;

import cn.wengzi.model.OrderDTO;
import cn.wengzi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OderHandler {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/type", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getOrderType() {
        OrderDTO order = new OrderDTO();
        order.setType("1");
        return orderService.handler(order);
    }
}
