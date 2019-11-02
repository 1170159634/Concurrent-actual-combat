package com.ycu.thread;

import com.sun.org.glassfish.external.statistics.Statistic;
import com.ycu.busi.impl;
import com.ycu.middle.model.Student;
import com.ycu.zhuangtai.status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.ycu.thread.consumer;
/*生产者*/
public class producter implements Runnable
{
    //相关数据库逻辑
    private impl impl1;
    //1.线程池
    private ThreadPoolExecutor executor;
    //2.异步容器
    private LinkedBlockingDeque<Runnable> runnables;
    //3.Logger日志信息
    private static final Logger logger=LoggerFactory.getLogger(producter.class);

    //为主main方法开启生产者线程做准备
public producter(impl impl1,ThreadPoolExecutor executor,LinkedBlockingDeque<Runnable> runnables)
{
    this.impl1=impl1;
    this.executor=executor;
    this.runnables=runnables;
}


    @Override
    public void run() {
        for (; ; ) {
            //1.获取相应的数据 （limit 10个数据）
            List<Student> list = impl1.haha(10);
            try {

                //2.判断状态 为空则休眠5秒
                if (list != null & list.size() > 0) {
                    //3.不为空，为中间表数据设置为处理状态（10f 处理中）
                    //以防下一次读取，然后再次存到最终表时 出错！！
                    impl1.updateStatus(list, status.DOING);
                  //从队列中读取一个线程，强制转换成consumer
                        consumer consumer1 = (consumer) runnables.take();
                        //注意点：它上一次保存的是上一次读取到”10I“的数据
                    //这一次set的是最新的数据
                        consumer1.setData(list);
                        //5.线程池开启一个线程，对consumer实施execute方法（处理消费者runnable服务==》填到最终表数据库）
                        executor.execute(consumer1);

                } else {

                    try {
                        TimeUnit.MILLISECONDS.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            } catch (Exception e) {
                logger.error("生产者发生错误！" + e);
                //如果期间发生严重错误，它的数据会被转为Error状态，根据loggger信息以便工程师查看哪里出错
                impl1.updateStatus(list,status.ERROR);
            }
        }
    }
}
