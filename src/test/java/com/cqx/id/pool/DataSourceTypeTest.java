package com.cqx.id.pool;

import com.cqx.id.pool.loader.DataSourceIdSegmentLoader;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataSourceTypeTest {

    @Test
    public void test0() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/discount?characterEncoding=UTF-8&useUnicode=true&useSSL=false&serverTimezone=GMT%2B8");
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        DataSourceIdSegmentLoader segmentLoader = new DataSourceIdSegmentLoader(dataSource);
        List<String> bizList = new ArrayList<>();
        bizList.add("test");
        IdPool.init(segmentLoader, bizList);

        for (int i = 0; i < 10000; i++) {
            String id = IdPool.nextId("test");
            System.out.println(id);
        }
    }

}
