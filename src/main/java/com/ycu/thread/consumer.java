package com.ycu.thread;

import com.ycu.chuli.middleimpl;
import com.ycu.middle.model.Student;
import com.ycu.zhuangtai.status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PublicKey;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
/*消费者*/
public class consumer  implements Runnable
{
    private  static final Logger LOGGER= LoggerFactory.getLogger(consumer.class);
    public List data;
    public middleimpl middleimplone;
    public LinkedBlockingDeque<Runnable> students;
    //1.消费者需要两个参数，一个是中间表的处理方法，另一个是main方法里队列
    //这个队列一直存有5个线程（具体看main方法）
    public consumer( middleimpl middleimplone,LinkedBlockingDeque<Runnable> students)
    {
        this.students=students;
        this.middleimplone=middleimplone;
    }
    public void setData(List data)
    {
        this.data=data;
    }
    @Override
    public void run()
    {

//2.根据最终表的格式，将中间表的数据放入到最终表中 our:最终表 middle：中间表
        middleimplone.deal(data);
        try {
            //3.将它放入Linked队列
            //4.为什么将它放入？ 为了生产者下一次调用时还有这个线程，5个线程真正做到循环利用
            students.put(this);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        }

    }
