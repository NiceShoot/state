package com.example.state.demo_2;

import com.example.state.StateApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.squirrelframework.foundation.fsm.StateMachineConfiguration;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

@RestController
public class OrderController {


    // 注入状态机
    @Autowired
    private OrderStateMachineEngine orderStateMachineEngine;
    @Autowired
    ApplicationContextUtil applicationContextUtil;


    @RequestMapping("/test")
    public void test(){
        OrderDTO orderDTO = new OrderDTO(OrderState.INIT);

        SubmitOrderStateMachine stateMachine = orderStateMachineEngine.stateMachineBuilder.newUntypedStateMachine(OrderState.INIT,
                StateMachineConfiguration.create().enableDebugMode(false).enableAutoStart(true),applicationContextUtil.getApplicationContext());
        stateMachine.addDeclarativeListener(new DeclarativeEventListener());

        OrderContext orderContext = new OrderContext(orderDTO,orderStateMachineEngine,stateMachine);

        orderStateMachineEngine.fire(stateMachine,OrderEvent.SUBMIT_ORDER,orderContext);
    }

}
