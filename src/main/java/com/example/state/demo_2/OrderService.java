package com.example.state.demo_2;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {


    public int submitOrder(OrderState state) {
        //log.info("submitOrder");
        return 1;
    }


}
