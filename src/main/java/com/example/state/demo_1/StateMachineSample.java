package com.example.state.demo_1;

import lombok.extern.slf4j.Slf4j;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.StateMachineConfiguration;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

/**
 * 定义状态机，支持如下格式的方法定义
 * transitFrom[fromStateName]To[toStateName]On[eventName]When[conditionName]
 * transitFrom[fromStateName]To[toStateName]On[eventName]
 * transitFromAnyTo[toStateName]On[eventName]
 * transitFrom[fromStateName]ToAnyOn[eventName]
 * transitFrom[fromStateName]To[toStateName]
 * on[eventName]
 * entry[eventName]
 * exit[eventName]
 */
@Slf4j
@StateMachineParameters(stateType = String.class,eventType = FSMEvent.class,contextType = Integer.class)
public class StateMachineSample extends AbstractUntypedStateMachine {

    // =========== A -> B：需要将方法名配置在 callMethod 内
    public void fromAToB(String from, String to, FSMEvent event, Integer context) {
        log.info("转换事件 {}=>{} on {} with {}.", from, to, event, context);
    }

    // =========== B -> C：符合 transitFrom[fromStateName]To[toStateName] 格式，不需要配置 callMethod
    public void transitFromBToC(String from, String to, FSMEvent event, Integer context) {
        log.info("转换事件 {}=>{} on {} with {}.", from, to, event, context);
    }

    // =========== 进入 A, 符合 entry[StateName] 格式，不需要配置 callMethod
    public void entryA(String from, String to, FSMEvent event, Integer context) {
        log.info("进入事件 {}=>{} on {} with {}.", from, to, event, context);
    }

    // =========== 退出 A, 符合 exit[StateName] 格式，不需要配置 callMethod
    public void exitA(String from, String to, FSMEvent event, Integer context) {
        log.info("退出事件 {}=>{} on {} with {}.", from, to, event, context);
    }

    // =========== 进入 B
    public void enterB(String from, String to, FSMEvent event, Integer context) {
        log.info("进入事件 {}=>{} on {} with {}.", from, to, event, context);
    }

    // =========== 退出 B
    public void exitB(String from, String to, FSMEvent event, Integer context) {
        log.info("退出事件 {}=>{} on {} with {}.", from, to, event, context);
    }

    // =========== 进入 C
    public void enterC(String from, String to, FSMEvent event, Integer context) {
        log.info("进入事件 {}=>{} on {} with {}.", from, to, event, context);
    }

    // =========== 退出 C
    public void exitC(String from, String to, FSMEvent event, Integer context) {
        log.info("退出事件 {}=>{} on {} with {}.", from, to, event, context);
    }

    // ==========================================================================================
    // 如果不想用 DeclarativeEventListener 这种声明在单独类里的方法，可以直接重写以下方法，效果是一样的
    // 两者同时用也可以，为了代码方便最好别这样
    // ==========================================================================================
    @Override
    protected void afterTransitionCausedException(Object fromState, Object toState, Object event, Object context) {
//        super.afterTransitionCausedException(fromState, toState, event, context);
        // 默认的实现是直接抛异常
        log.info("Override 发生错误 {}", getLastException().getMessage());
    }

    @Override
    protected void beforeTransitionBegin(Object fromState, Object event, Object context) {
        // 转换开始时被调用
        System.err.println("开始Transition");
        super.beforeTransitionBegin(fromState, event, context);
        log.info("Override beforeTransitionBegin");
    }

    @Override
    protected void afterTransitionCompleted(Object fromState, Object toState, Object event, Object context) {
        // 转换完成时被调用
        // System.err.println("完成Transition");
        super.afterTransitionCompleted(fromState, toState, event, context);
        log.info("Override afterTransitionCompleted");
    }

    @Override
    protected void afterTransitionEnd(Object fromState, Object toState, Object event, Object context) {
        // 转换结束时被调用
        //System.err.println("结束Transition");
        super.afterTransitionEnd(fromState, toState, event, context);
        log.info("Override afterTransitionEnd");
        System.out.println();
    }

    @Override
    protected void afterTransitionDeclined(Object fromState, Object event, Object context) {
        // 当转换被拒绝时被调用。实际是调用 callMethod 中的方法被调用时，抛出异常时被调用
        super.afterTransitionDeclined(fromState, event, context);
        log.info("Override afterTransitionDeclined");
    }

    @Override
    protected void beforeActionInvoked(Object fromState, Object toState, Object event, Object context) {
        // 当转换开始时被调用。实际是 callMethod 中的方法被调用时，类似于 AOP 的效果，运行一下即可知道
        super.beforeActionInvoked(fromState, toState, event, context);
        log.info("Override beforeActionInvoked");
    }

    @Override
    protected void afterActionInvoked(Object fromState, Object toState, Object event, Object context) {
        // 当转换结束时被调用。实际是 callMethod 被调用时，类似于 AOP 的效果，运行一下即可知道
        super.afterActionInvoked(fromState, toState, event, context);
        log.info("Override afterActionInvoked");
    }

    public static void main(String[] args) {
        // 构造 builder
        StateMachineConfiguration stateMachineConfiguration = StateMachineConfiguration.create()
                .enableAutoStart(false);
        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(StateMachineSample.class);
        builder.setStateMachineConfiguration(stateMachineConfiguration);
        // 配置状态流转时触发的事件
        builder.externalTransition().from("A").to("B").on(FSMEvent.ToB).callMethod("fromAToB");
        builder.externalTransition().from("B").to("C").on(FSMEvent.ToC);
        builder.onEntry("A");
        builder.onExit("A");
        builder.onEntry("B").callMethod("enterB");
        builder.onExit("B");
        builder.onEntry("C").callMethod("enterC");
        builder.onExit("C");
        // 使用状态机
        UntypedStateMachine fsm = builder.newStateMachine("A");
        fsm.start();
        // 添加监听器
//        fsm.addStateMachineListener(new StateMachineListener<UntypedStateMachine, Object, Object, Object>() {
//            @Override
//            public void stateMachineEvent(StateMachineEvent<UntypedStateMachine, Object, Object, Object> event) {
//                log.info("lastState: " + event.getStateMachine().getLastState());
//            }
//        });
        fsm.addDeclarativeListener(new DeclarativeEventListener());
        // 源码中的日志 demo
//        StateMachineLogger logger = new StateMachineLogger(fsm);
//        logger.startLogging();
        fsm.fire(FSMEvent.ToB, 1);
        fsm.fire(FSMEvent.ToC,2);
        System.out.println("Current state is " + fsm.getCurrentState());
    }



}
