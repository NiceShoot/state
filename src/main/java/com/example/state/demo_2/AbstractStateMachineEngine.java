package com.example.state.demo_2;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.GenericTypeResolver;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.StateMachineConfiguration;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;

/**
 * spring集成squirrel-foundation
 * (1).通过Spring创建StateMachineBuilder实例(单例)；
 *
 * (2).业务函数中通过(1)的StateMachineBuilder实例创建StateMachine实例，
 *      并向StateMachine暴露SpringApplicationContext，以便于StateMachine通过ApplicationContext获取数据层的对象。
 */
public class AbstractStateMachineEngine <T extends UntypedStateMachine> implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    protected UntypedStateMachineBuilder stateMachineBuilder = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public AbstractStateMachineEngine() {
        //识别泛型参数
        Class<T> genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(),
                AbstractStateMachineEngine.class);
        stateMachineBuilder = StateMachineBuilderFactory.create(genericType, ApplicationContext.class);
    }

    public void fire(T stateMachine,OrderEvent event, OrderContext context) {
        stateMachine.fire(event, context);
    }


}
