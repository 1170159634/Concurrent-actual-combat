package com.ycu.busi;
import com.ycu.datasource.factory;
import com.ycu.middle.mapping.StudentMapper;
import com.ycu.middle.model.Student;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
/*处理中间表的类！！！ 包括 1.查询中间表中是否有状态为”10I的“
*                          2.修改状态为”10F“*/
public class impl
{
    private static final Logger LOGGER= LoggerFactory.getLogger(impl.class);



    public List<Student> haha(int count)
    {
        List<Student> list = null;
        SqlSession sqlSession=factory.getSqlSession("middle");
       StudentMapper ok=sqlSession.getMapper(StudentMapper.class);
    try {
        list=ok.haha(count);
        sqlSession.commit();
    }
    catch (Exception e)
    {
        LOGGER.error("查询时出现错误======>"+e);
        e.printStackTrace();
        sqlSession.rollback();
    }
    finally {
    sqlSession.close();
    }
        return list;
    }



    public int  updateStatus(List data,String status)
    {
        SqlSession sqlSession=factory.getSqlSession("middle");
        StudentMapper ok=sqlSession.getMapper(StudentMapper.class);

        try {
          List<Student> list = data;
          list.forEach(e->{
              //在遍历列表时，同时设置状态值
              e.setDataStatus(status);
              ok.updateStatus(e);
          });
           sqlSession.commit();
        }
        catch (Exception e)
        {
            LOGGER.error("修改状态时出现错误======>"+e);
            e.printStackTrace();
            sqlSession.rollback();
        }
        finally {
         sqlSession.close();
        }

        return 1;

    }
}
