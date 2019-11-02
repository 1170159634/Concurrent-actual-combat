package com.ycu.datasource;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
/*饿汉式范例： 创建两个sqlsession工厂（最终表工厂和中间表工厂） 避免多次创建 消耗资源*/
public class factory {


    private static SqlSessionFactory ourSqlSessionFactory;
    private static SqlSessionFactory middleSqlSessionFactory;
    private static final String OUR = "our";
    private static final String MIDDLE = "middle";

    private static final String MIDDLE_MIDDLE = "mybatis-config-middle.xml";
    private static final String OUR_MIDDLE = "mybatis-config-our.xml";
    private static Reader middleResourceAsReader = null;
    private static Reader ourResourceAsReader = null;
    static {
        try {
            middleResourceAsReader = Resources.getResourceAsReader(MIDDLE_MIDDLE);
            ourResourceAsReader = Resources.getResourceAsReader(OUR_MIDDLE);

            middleSqlSessionFactory =  new SqlSessionFactoryBuilder().build(middleResourceAsReader);
            ourSqlSessionFactory =  new SqlSessionFactoryBuilder().build(ourResourceAsReader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                middleResourceAsReader.close();
                ourResourceAsReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取sqlsession
     * @param code
     * @return
     */
    public static SqlSession getSqlSession(String code) {
        if (code.equals(MIDDLE)) {
            return middleSqlSessionFactory.openSession();
        }
        return ourSqlSessionFactory.openSession();
    }
}
