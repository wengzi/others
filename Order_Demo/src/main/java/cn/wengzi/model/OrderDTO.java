package cn.wengzi.model;

import lombok.*;

import java.math.BigDecimal;

/**
 * @author leizzige
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderDTO {
    private String code;
    private BigDecimal price;

    /**
     * 订单类型
     * 1:普通订单
     * 2:团购订单
     * 3:促销订单
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
