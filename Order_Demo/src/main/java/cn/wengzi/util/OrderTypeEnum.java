package cn.wengzi.util;

/**
 * 订单类型枚举
 *
 * @author leizige
 */
public enum OrderTypeEnum {

    COMMON("1", "普通订单"),

    GROUP("2", "团购订单"),

    SALES("3", "促销订单");

    private String typeName;
    private String typeCode;

    OrderTypeEnum(String typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return this.typeCode;
    }


}
