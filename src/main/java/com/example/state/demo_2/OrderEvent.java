package com.example.state.demo_2;

public enum OrderEvent {
    SUBMIT_ORDER,
    PAY,
    PART_SEND,
    SEND,
    COMPLETE;



    public static OrderEvent getNextEvent(OrderEvent event){
        switch (event){
            case SUBMIT_ORDER:
                return PAY;
            case PAY:
                return PART_SEND;
            case PART_SEND:
                return SEND;
            case SEND:
                return COMPLETE;
            default:
                return null;
        }
    }
}
