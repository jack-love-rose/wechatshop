package com.imooc.wechatshop.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;


/**
 * 线程池
 */

@Slf4j
public class ThreadPoolDemo {

    private static  int count = 0;


    /**
     * corePoolSize，核心线程池大小，一般线程池会至少保持这么多的线程数量；
     * maximumPoolSize，最大线程池大小，也就是线程池最大的线程数量；
     * keepAliveTime和unit共同组成了一个超时时间，keepAliveTime是时间数量，unit是时间单位，单位加数量组成了最终的超时时间。这个超时时间表示如果线程池中包含了超过corePoolSize数量的线程，则在有线程空闲的时间超过了超时时间时该线程就会被销毁；
     * workQueue是任务的阻塞队列，在没有线程池中没有足够的线程可用的情况下会将任务先放入到这个阻塞队列中等待执行。这里传入的队列类型就决定了线程池在处理这些任务时的策略，具体类型会在下文中介绍；
     * threadFactory，线程的工厂对象，线程池通过该对象创建线程。我们可以通过传入自定义的实现了ThreadFactory接口的类来修改线程的创建逻辑，可以不传，默认使用Executors.defaultThreadFactory()作为默认的线程工厂；
     * handler，拒绝策略，在线程池无法执行或保存新提交的任务时进行处理的对象，常用的有以下几种策略类：
     * ThreadPoolExecutor.AbortPolicy，默认策略，行为是直接抛出RejectedExecutionException异常
     * ThreadPoolExecutor.CallerRunsPolicy，用调用者所在的线程来执行任务
     * ThreadPoolExecutor.DiscardOldestPolicy，丢弃阻塞队列中最早提交的任务，并重试execute方法
     * ThreadPoolExecutor.DiscardPolicy，静默地直接丢弃任务，不返回任何错误
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Runnable task = new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 10000; ++i) {
                    synchronized (ThreadPoolDemo.class) {
                        count += 1;
                    }
                }
            }
        };

        // 重要：创建线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(30, 50, 100L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        // 重要：向线程池提交两个任务
        threadPool.execute(task);
        threadPool.execute(task);

        //submit和execute的区别在于 submit有返回值 可以取消啥的
        Future futureResult = threadPool.submit(task);

        //取消这个线程的执行结果
        futureResult.cancel(true);

        //查看已存在线程个数
        log.info("已存在线程数量：" + threadPool.getActiveCount());

        // 等待线程池中的所有任务完成,调用shutdown关闭服务，提交已完成的任务并且不再接收新的任务
        threadPool.shutdown();

        while (!threadPool.awaitTermination(100L, TimeUnit.MILLISECONDS)) {
            log.info("Not yet. Still waiting for termination");
        }

        log.info("count = " + count);
    }
}