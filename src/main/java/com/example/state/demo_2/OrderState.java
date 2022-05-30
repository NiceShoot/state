package com.example.state.demo_2;

public enum OrderState {

    INIT,

    WAIT_PAY,

    WAIT_SEND,

    PART_SEND,

    WAIT_RECEIVE,

    COMPLETE,

    CANCELED;

    public static OrderState getState(String state) {
        for (OrderState orderState : OrderState.values()) {
            if (orderState.name().equalsIgnoreCase(state)) {
                return orderState;
            }
        }
        return null;
    }

    public static OrderState getNextState(OrderState state){
        switch (state){
            case INIT:
                return WAIT_PAY;
            case WAIT_PAY:
                return WAIT_SEND;
            case WAIT_SEND:
                return PART_SEND;
            case PART_SEND:
                return WAIT_RECEIVE;
            case WAIT_RECEIVE:
                return COMPLETE;
            case COMPLETE:
                return CANCELED;
            default:
                return null;
        }
    }

}
