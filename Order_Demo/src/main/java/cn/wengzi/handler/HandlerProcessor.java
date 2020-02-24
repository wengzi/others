package cn.wengzi.handler;

import cn.wengzi.util.OrderTypeEnum;
import cn.wengzi.annotation.OrderType;
import cn.wengzi.util.ClassScanner;
import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@SuppressWarnings("all")
public class HandlerProcessor implements BeanFactoryPostProcessor {

    private final String HANDLER_PACKAGE = "cn.wengzi.handler";

    /**
     * 扫描@OrderType,初始化HandlerContext并将其注入到Spring容器
     *
     * @param beanFactory
     * @throws BeansException
     * @see OrderType
     * @see HandlerContext
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, Class<?>> handlerMap = Maps.newHashMapWithExpectedSize(16);
        ClassScanner.scan(HANDLER_PACKAGE, OrderType.class).forEach(clazz -> {
            OrderTypeEnum type = clazz.getAnnotation(OrderType.class).value();
            handlerMap.put(type.getTypeCode(), clazz);
        });
        HandlerContext context = new HandlerContext(handlerMap);
        beanFactory.registerSingleton(HandlerContext.class.getName(), context);
    }
}
