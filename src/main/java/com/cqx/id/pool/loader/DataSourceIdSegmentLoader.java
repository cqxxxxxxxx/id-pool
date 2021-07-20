package com.cqx.id.pool.loader;

import com.cqx.id.pool.IdPoolEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataSourceIdSegmentLoader extends AbstractIdSegmentLoader {
    private static final String SELECT_SQL = "select * from id_pool where biz = ?";
    private static final String UPDATE_SQL = "update id_pool set id_cur = ? where biz = ? and id_cur = ?";
    private DataSource dataSource;

    public DataSourceIdSegmentLoader(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected IdPoolEntity loadSegment(String biz) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_SQL);
            preparedStatement.setString(1, biz);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                IdPoolEntity idPoolEntity = new IdPoolEntity();
                idPoolEntity.setBiz(biz);
                idPoolEntity.setCacheSize(rs.getInt("cache_size"));
                idPoolEntity.setIdCur(rs.getLong("id_cur"));
                idPoolEntity.setIdMin(rs.getLong("id_min"));
                idPoolEntity.setIdMax(rs.getLong("id_max"));
                idPoolEntity.setPattern(rs.getString("pattern"));
                idPoolEntity.setStrategy(rs.getString("strategy"));
                idPoolEntity.setLoadFactor(rs.getDouble("load_factor"));
                return idPoolEntity;
            }
            throw new IllegalArgumentException("biz [" + biz + "] no record found in db");
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    protected boolean updateSegment(String biz, Long expectId, Long updateId) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_SQL);
            preparedStatement.setLong(1, updateId);
            preparedStatement.setString(2, biz);
            preparedStatement.setLong(3, expectId);
            int i = preparedStatement.executeUpdate();
            return i == 1;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }


}
