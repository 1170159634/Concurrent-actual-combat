package com.ycu.middle.mapping;

import com.ycu.middle.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Objects;
/*此接口为中间表的接口 */
@Mapper
public interface StudentMapper
{
    @Select("select id, name, sex, birth, department, add_time, data_status, deal_time from student where data_status='10I' limit #{count}")
    List<Student> haha(int count);
    @Update("update student set data_status=#{dataStatus} where id=#{id}")
    int updateStatus(Student student);
}
