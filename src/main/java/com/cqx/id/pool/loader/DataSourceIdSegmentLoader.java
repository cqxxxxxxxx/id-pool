package com.cqx.id.pool.loader;

import com.cqx.id.pool.IdPoolEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSourceIdSegmentLoader extends AbstractIdSegmentLoader {
    private static final String SELECT_SQL = "select * from id_pool where biz = ?";
    private static final String UPDATE_SQL = "update id_pool set id_cur = ? where biz = ? and id_cur = ?";
    private DataSource dataSource;

    public DataSourceIdSegmentLoader(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected IdPoolEntity loadSegment(String biz) throws Exception {
        PreparedStatement preparedStatement = prepareStatement(SELECT_SQL);
        preparedStatement.setString(1, biz);
        ResultSet rs = preparedStatement.executeQuery();
        IdPoolEntity idPoolEntity = new IdPoolEntity();
        idPoolEntity.setBiz(biz);
        idPoolEntity.setCacheSize(rs.getInt("cache_size"));
        idPoolEntity.setIdCur(rs.getLong("id_cur"));
        idPoolEntity.setIdMin(rs.getLong("id_min"));
        idPoolEntity.setIdMax(rs.getLong("id_max"));
        idPoolEntity.setPattern(rs.getString("pattern"));
        idPoolEntity.setStrategy(rs.getString("strategy"));
        return idPoolEntity;
    }

    @Override
    protected boolean updateSegment(String biz, Long expectId, Long updateId) throws Exception {
        PreparedStatement preparedStatement = prepareStatement(UPDATE_SQL);
        preparedStatement.setLong(1, updateId);
        preparedStatement.setString(2, biz);
        preparedStatement.setLong(3, expectId);
        int i = preparedStatement.executeUpdate();
        return i == 1;
    }

    private PreparedStatement prepareStatement(String sql) throws Exception {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement;
    }
}
