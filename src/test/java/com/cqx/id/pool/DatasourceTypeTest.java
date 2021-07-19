package com.cqx.id.pool;

import com.cqx.id.pool.loader.DatasourceIdSegmentLoader;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatasourceTypeTest {

    @Test
    public void test0() {
        MysqlDataSource dataSource = new MysqlDataSource();
        Properties properties = new Properties();
        properties.setProperty("host", "124.70.178.185");
        properties.setProperty("port", "3306");
        properties.setProperty("user", "root");
        properties.setProperty("password", "123456");
        dataSource.initializeProperties(properties);
        DatasourceIdSegmentLoader segmentLoader = new DatasourceIdSegmentLoader(dataSource);
        List<String> bizList = new ArrayList<>();
        bizList.add("test");
        IdPool.init(segmentLoader, bizList);

        for (int i = 0; i < 10000; i++) {
            String id = IdPool.nextId("test");
            System.out.println(id);
        }
    }

}
