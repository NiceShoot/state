package com.example.state.demo_2;

import lombok.extern.slf4j.Slf4j;
import org.squirrelframework.foundation.exception.TransitionException;
import org.squirrelframework.foundation.fsm.Action;
import org.squirrelframework.foundation.fsm.annotation.*;

/**
 * 监听器
 */
@Slf4j
public class DeclarativeEventListener {

    /**
     * 转换事件开始时进行调用
     * @param event
     */
    @OnTransitionBegin
    public void transitionBegin(OrderEvent event) {
        log.info("transitionBegin event {}", event);
    }

    /**
     * 转换事件开始时进行调用
     * 可以加入条件
     * 'event'(E), 'from'(S), 'to'(S), 'context'(C) and 'stateMachine'(T) can be used in MVEL scripts
     */
    @OnTransitionBegin(when="event.name().equals(\"INIT\")")
    public void transitionBeginConditional() {
        log.info("transitionBeginConditional");
    }

    /**
     * 转换事件结束时进行调用
     * 这个方法必须是 public 并且返回值是 void
     */
    @OnTransitionEnd
    @ListenerOrder(10)
    public void transitionEnd(OrderEvent event,OrderContext context) {
        log.info("transitionEnd");
        System.out.println();
        OrderState nextState = OrderState.getNextState(context.orderDTO.getState());
        OrderEvent nextEvent = OrderEvent.getNextEvent(event);
        if (nextEvent == null || nextState == null){
            return;
        }
        OrderStateMachineEngine orderStateMachineEngine = context.getOrderStateMachineEngine();
        context.setOrderDTO(new OrderDTO(nextState));
        SubmitOrderStateMachine stateMachine = context.getStateMachine();
        orderStateMachineEngine.fire(stateMachine,nextEvent,context);
    }

    @OnTransitionComplete
    public void transitionComplete(OrderState from, OrderState to, OrderEvent event, OrderContext context) {
        log.info("transitionComplete {}=>{} on {} with {}", from, to, event, context.getOrderDTO().getState());
    }

    @OnTransitionException
    public void transitionException(OrderState from, OrderState to, OrderEvent event, OrderContext context) {
        log.info("transitionException");
    }

    /**
     * 当转换被拒绝时，将调用注有TransitionDecline的方法
     */
    @OnTransitionDecline
    public void transitionDeclined(OrderState from, OrderEvent event, OrderContext context) {
        log.info("transitionDeclined {}=>??? on {} with {}", from, event, context);
    }

    /**
     * 带有 OnAfterActionExecuted 注释的方法将在调用操作之前被调用
     * 实际是 callMethod 中的方法被调用钱执行这个方法。类似于 AOP 的效果，运行一下即可知道
     */
    @OnBeforeActionExecuted
    public void onBeforeActionExecuted(Object sourceState, Object targetState,
                                       Object event, Object context, int[] mOfN, Action<?, ?, ?,?> action) {
        log.info("onBeforeActionExecuted");
    }

    /**
     * 带有 OnAfterActionExecuted 注释的方法将在调用操作之后被调用
     * 实际是 callMethod 中的方法被调用后执行这个方法。类似于 AOP 的效果，运行一下即可知道
     */
    @OnAfterActionExecuted
    public void onAfterActionExecuted(Object sourceState, Object targetState,
                                      Object event, Object context, int[] mOfN, Action<?, ?, ?,?> action) {
        log.info("onAfterActionExecuted");
    }

    /**
     * 带有 OnActionExecException 注释的方法将在调用操作异常之后被调用
     * 实际是 callMethod 中的方法被调用时抛异常了之后执行这个方法。类似于 AOP 的效果，运行一下即可知道
     */
    @OnActionExecException
    public void onActionExecException(Action<?, ?, ?,?> action, TransitionException e) {
        log.info("onActionExecException");
    }
}
