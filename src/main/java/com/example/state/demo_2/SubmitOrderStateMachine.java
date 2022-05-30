package com.example.state.demo_2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.annotation.*;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;
@States({
        @State(name = "INIT", entryCallMethod = "entryStateInit", exitCallMethod = "exitStateInit", initialState = true),
        @State(name = "WAIT_PAY", entryCallMethod = "entryStateWaitPay", exitCallMethod = "exitStateWaitPay"),
        @State(name = "WAIT_SEND", entryCallMethod = "entryStateWaitSend", exitCallMethod = "exitStateWaitSend"),
        @State(name = "PART_SEND", entryCallMethod = "entryStatePartSend", exitCallMethod = "exitStatePartSend"),
        @State(name = "WAIT_RECEIVE", entryCallMethod = "entryStateWaitReceive", exitCallMethod = "exitStateWaitReceive"),
        @State(name = "COMPLETE", entryCallMethod = "entryStateComplete", exitCallMethod = "exitStateComplete")
})
@Transitions({
        @Transit(from = "INIT", to = "WAIT_PAY", on = "SUBMIT_ORDER", callMethod = "submitOrder"),
        @Transit(from = "WAIT_PAY", to = "WAIT_SEND", on = "PAY", callMethod = "pay"),
        @Transit(from = "WAIT_SEND", to = "PART_SEND", on = "PART_SEND", callMethod = "partSend"),
        @Transit(from = "PART_SEND", to = "WAIT_RECEIVE", on = "SEND", callMethod = "send"),
        @Transit(from = "WAIT_RECEIVE", to = "COMPLETE", on = "COMPLETE", callMethod = "complete")
})
@StateMachineParameters(stateType = OrderState.class, eventType = OrderEvent.class, contextType = OrderContext.class)
@Slf4j
public class SubmitOrderStateMachine extends AbstractStateMachine<UntypedStateMachine, Object, Object, Object> implements UntypedStateMachine{

    private OrderService orderService;

    protected ApplicationContext applicationContext;

    public SubmitOrderStateMachine(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        orderService = applicationContext.getBean(OrderService.class);
    }

    public void submitOrder(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        orderService.submitOrder(toState);
        log.info("submitOrder");
    }

    public void pay(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("pay");
    }

    public void partSend(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("partSend");
    }

    public void send(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("send");
    }

    public void complete(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("complete");
    }

    public void entryStateInit(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("entryStateInit");
    }

    public void exitStateInit(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("exitStateInit");
    }

    public void entryStateWaitPay(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("entryStateWaitPay");
    }

    public void exitStateWaitPay(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("exitStateWaitPay");
    }

    public void entryStateWaitSend(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("entryStateWaitSend");
        // orderContext.getOrderStateMachineEngine().fire(OrderEvent.PART_SEND,orderContext);
    }

    public void exitStateWaitSend(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("exitStateWaitSend");
    }

    public void entryStatePartSend(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("entryStatePartSend");
        // orderContext.getOrderStateMachineEngine().fire(OrderEvent.SEND,orderContext);
    }

    public void exitStatePartSend(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("exitStatePartSend");
    }

    public void entryStateWaitReceive(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("entryStateWaitReceive");
        // orderContext.getOrderStateMachineEngine().fire(OrderEvent.SEND,orderContext);
    }

    public void exitStateWaitReceive(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("exitStateWaitReceive");
    }

    public void entryStateComplete(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("entryStateComplete");
    }

    public void exitStateComplete(OrderState fromState, OrderState toState, OrderEvent orderEvent, OrderContext orderContext) {
        log.info("exitStateComplete");
    }


}
