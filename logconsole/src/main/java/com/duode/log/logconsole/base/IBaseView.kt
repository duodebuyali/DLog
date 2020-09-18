package com.duode.log.logconsole.base

import android.content.Context
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent

/**
 * @author hekang
 * @des 用来提供view上需要响应的基础方法
 * @date 2020/9/14 10:39
 */
interface IBaseView {

  /**
   * 指定绑定到activity的某个声明周期事件
   * */
  fun <T> bindEvent(event: ActivityEvent): LifecycleTransformer<T>?

  /**
   * 指定绑定到fragment的某个声明周期事件
   * */
  fun <T> bindEvent(event: FragmentEvent): LifecycleTransformer<T>?

  /**
   * 指定绑定到实现了LifecycleProvider的某个view上
   * */
  fun <T> bindLifecycle(): LifecycleTransformer<T>

  fun getContext(): Context?

  /**
   * 创建每个view对应的网络请求标志信息
   * @param groupTag 要将其分类的组
   * @param selfTag 单独的标志
   * *//*
  fun buildNetTagInfo(groupTag: String, selfTag: String): NetTagInfo*/
}