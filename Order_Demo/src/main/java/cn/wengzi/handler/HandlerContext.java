package cn.wengzi.handler;

import cn.wengzi.util.BeanTool;

import java.util.Map;

/**
 * @Description: 处理器上下文, 根据类型获取相应的处理器
 */
@SuppressWarnings("unchecked")
public class HandlerContext {

    private Map<String, Class<?>> handlerMap;

    public HandlerContext(Map<String, Class<?>> handlerMap) {
        this.handlerMap = handlerMap;
    }

    /**
     * 根据类型获取对应的class,然后根据class类型获取注册到spring中的bean
     *
     * @param type
     * @return
     */
    public AbstractHandler getInstance(String type) {
        Class clazz = handlerMap.get(type);
        if (null == clazz) {
            throw new IllegalArgumentException("not found handler for type" + type);
        }
        return (AbstractHandler) BeanTool.getBean(clazz);
    }
}
