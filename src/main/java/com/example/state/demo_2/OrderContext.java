package com.example.state.demo_2;

/**
 * 上下文
 */
public class OrderContext {


    public OrderContext(OrderDTO orderDTO, OrderStateMachineEngine orderStateMachineEngine, SubmitOrderStateMachine stateMachine) {
        this.orderDTO = orderDTO;
        this.orderStateMachineEngine = orderStateMachineEngine;
        this.stateMachine = stateMachine;
    }

    public OrderDTO orderDTO;

    private OrderStateMachineEngine orderStateMachineEngine;

    private SubmitOrderStateMachine stateMachine;

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public OrderStateMachineEngine getOrderStateMachineEngine() {
        return orderStateMachineEngine;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public void setOrderStateMachineEngine(OrderStateMachineEngine orderStateMachineEngine) {
        this.orderStateMachineEngine = orderStateMachineEngine;
    }

    public SubmitOrderStateMachine getStateMachine() {
        return stateMachine;
    }

    public void setStateMachine(SubmitOrderStateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }
}
