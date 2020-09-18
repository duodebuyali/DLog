package com.duode.log.logconsole.base

/**
 * @author hekang
 * @des 所有present需要继承这个
 * @date 2020/9/14 10:59
 *
 * 在ISubscribe管理网络请求的生命周期;需要在present中将网络请求加入缓存管理,以达成让用户可以取消指定请求的功能
 */
abstract class BasePresenter<V : IBaseView>(baseView: V) {
}