package com.duode.jitpacklib.utils

/**
 * @author hekang
 * @des 通用的onStart 和onCompleted处理
 * @date 2020/12/10 15:38
 */
object CommonObserverManager {

    /**
     * 默认的统一处理协程任务开始执行前的操作
     * 后续确认统一的loading提示dialogFragment之后可以使用
     * */
    var onStart:(() -> Unit)? = null

    /**
     * 默认的完成Coroutine的处理
     * */
    var onCompleted:(() -> Unit)? = null
}