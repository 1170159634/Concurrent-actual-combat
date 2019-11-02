package com.ycu.Main;

import com.ycu.busi.impl;
import com.ycu.chuli.middleimpl;
import com.ycu.thread.consumer;
import com.ycu.thread.producter;

import java.util.concurrent.*;

/*模拟生产者  消费者*/
public class main
{
    public static void main(String[] args)
    {
        //1.数据库的两个mapper  一个是中间表的mapper
        impl implone=new impl();
        //一个是最终表的mapper
        middleimpl impltwo=new middleimpl();
        //2.创建异步容器 里面最多可以存放10个runnable
        LinkedBlockingDeque<Runnable> runnables=new LinkedBlockingDeque<>(10);
        //3.创建线程池
       ThreadPoolExecutor executor= new ThreadPoolExecutor(10,20,6L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(20));
   for( int i=0;i<5;i++)
   {
       try {
           //我们这里为了测试，放入了5个消费者线程
           runnables.put(new consumer(impltwo, runnables ));
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
   }
   //生产者必须将上面LinkedDeque带入（保证队列里有消费者线程存在）
        producter producter1=new producter(implone, executor, runnables);
new Thread(producter1).start();

    }
}
