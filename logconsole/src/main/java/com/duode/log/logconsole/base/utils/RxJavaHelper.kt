package com.duode.log.logconsole.base.utils

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

/**
 * @author hekang
 * @des 这里是相关rxJava线程调度的一些方法，本来是不需要写在类中，但是这里为了后续方便查找还是写在类中
 * @date 2020/9/10 16:37
 *
 * Schedulers调度参考: https://www.jianshu.com/p/6637724b094f
 */
object RxJavaHelper {

    /**
     * 自定义的一个线程池
     * */
    private val mSchedulerPool by lazy {
        val executor = Executors.newFixedThreadPool(10)
        val pooledScheduler = Schedulers.from(executor)

        pooledScheduler
    }

    /**
     * 在io线程进行任务，在主线程回调结果
     *
     * @see Schedulers.io
     * 最常见的调度器之一。它们用于IO相关操作。比如网络请求和文件操作。IO 调度器背后由线程池支撑。
     * 它首先创建一个工作线程，可以复用于其他操作。当然，当这个工作线程(长时间任务的情况)不能被复用时，会创建一个新的线程来处理其他操作。
     * 这个好处也会带来一些问题，因为允许创建的线程是无限的，所以当创建大量线程的时候，会对整体性能造成一些影响
     * */
    fun <T> Flowable<T>.standSubc(): Flowable<T> {
        return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 在io线程进行任务，在主线程回调结果
     * */
    fun <T> Observable<T>.standSubc(): Observable<T> {
        return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 在io线程进行任务，在主线程回调结果
     * */
//  fun <T> Completable.standSubc(t: T): Flowable<T> {
//    return this.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .toFlowable<T>()
//  }
    fun Completable.standSubc(): Completable {
        return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 防止由于同一时间执行的方法太多，导致创建的io线程超出限制
     * 一般使用在短期类会多次运行的任务
     * @see Schedulers.computation
     * 这个调度器和IO调度器非常相似，因其也是由线程池支持的。然鹅，可用的线程数是固定的，和系统的cpu核数目保持一致。
     * 所以如果你的手机是双核的，那么线程池中就有两个线程。这也意味着如果这两条线程都处于忙碌状态，那么该进程将会等待线程变成空闲状态的时候才能处理其他操作。
     * 因为这个限制，它不太适合IO相关操作。适用于进行一些计算操作，计算速度还很快。
     * */
    fun Completable.computationSubc(): Completable {
        return this.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 适用于多个任务需要按顺序执行的情况
     * @see Schedulers.single
     * 此款调度器非常简单，由一个线程支持。所以无论有多少个observables,都将只运行在这个线程上
     * */
    fun Completable.singleSubc(): Completable {
        return this.subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 在io线程进行任务，在主线程回调结果
     * */
    fun <T> Single<T>.standSubc(): Flowable<T> {
        return this.toFlowable().standSubc()
    }

    /**
     * 在io线程进行任务，在主线程回调结果
     * */
    fun <T> Maybe<T>.standSubc(): Flowable<T> {
        return this.toFlowable().standSubc()
    }


}