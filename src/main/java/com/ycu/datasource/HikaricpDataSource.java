package com.ycu.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
/*使用的Datasource格式 java名称需一致*/
public class HikaricpDataSource extends UnpooledDataSourceFactory {
    public HikaricpDataSource() {
        this.dataSource = new HikariDataSource();
    }
}
