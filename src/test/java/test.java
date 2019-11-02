import com.ycu.busi.impl;
import com.ycu.chuli.middleimpl;
import com.ycu.middle.model.Student;
import org.junit.Test;

import java.sql.Struct;
import java.util.List;
import java.util.Objects;

public class test
{
    @Test
    public void quertList()
    {
        impl impl1=new impl();
        List<Student> list=impl1.haha(10);
        middleimpl middleimpl1=new middleimpl();
        middleimpl1.deal(list);
//        Student student=new Student();
//        student.setDataStatus("10I");
//        student.setId(2);
//        int b=impl1.updateStatus(student);
//        System.out.println(b);
//        List<Student> list = impl1.haha(2);
//        System.out.println(list);

    }

}
