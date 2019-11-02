package com.ycu.our.mapping;

import com.ycu.middle.model.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
/*此为最终表的接口 实现两个功能*/
@Mapper
public interface StudentMapper
{
    @Update("update student set data_status=#{dataStatus} where id=#{id}")
    int updateStatus(com.ycu.our.model.Student student);
    @Insert("insert into student (id,name,sex,birth, department, add_time, data_status, deal_time) values(#{id},#{name},#{sex},#{birth}, #{department}, #{addTime},#{dataStatus}, #{dealTime})")
    int insert(com.ycu.our.model.Student student);
}
