package com.ycu.chuli;

import com.ycu.datasource.factory;
import com.ycu.middle.mapping.StudentMapper;
import com.ycu.our.model.Student;
import com.ycu.zhuangtai.status;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
/*处理最终表的类！！  功能：1.将中间表的数据格式转换为最终表的数据格式
*                          2.将List数据（转换好的）传递到最终表*/
public class middleimpl
{
    private static final Logger LOGGER = LoggerFactory.getLogger(middleimpl.class);
    public void deal(List data)
    {

            //1.首先获取转换后的格式
            List<Student> list = updateGeshi(data);
            //2.遍历数据
            list.forEach(student -> {
                SqlSession sqlSession = factory.getSqlSession("our");
                com.ycu.our.mapping.StudentMapper mapper = sqlSession.getMapper(com.ycu.our.mapping.StudentMapper.class);

                try {
                    //3.将数据发送到最终表
                    mapper.insert(student);
                    //4.提交事务，代表传递数据成功
                    sqlSession.commit();
                    //5.设置数据的状态 （10F==> 转入成功！）
                    student.setDataStatus(status.FINSISH);
                    mapper.updateStatus(student);
                }  catch (Exception e)
                    {
                        e.printStackTrace();
                        LOGGER.error("deal方法传送数据时出现错误！",e);
                        student.setDataStatus(status.ERROR);
                        mapper.updateStatus(student);
                    }finally {
                    sqlSession.close();
                }
            });
        }


    public List<Student> updateGeshi(List<com.ycu.middle.model.Student> data)
    {
        //1.将中间表的数据格式转换为最终表的格式
        List<Student> list =new ArrayList<>();
        data.forEach(e->{
            Student student=new Student();
            student.setAddTime(e.getAddTime());
            student.setBirth(e.getBirth());
            student.setDataStatus(e.getDataStatus());
            student.setSex(e.getSex());
            student.setDepartment(e.getDepartment());
            student.setName(e.getName());
            student.setId(e.getId());
            student.setDealTime(e.getDealTime());
            student.setDataStatus(status.FINSISH);
            list.add(student);
        });
        return list;
    }
}
