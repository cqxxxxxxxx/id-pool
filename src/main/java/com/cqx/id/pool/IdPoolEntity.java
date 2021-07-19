package com.cqx.id.pool;

/**
 * id_cur     int comment '当前id'
 * primary key,
 * id_min     int             not null,
 * id_max     int             not null,
 * biz    varchar(50)     null comment '业务类型',
 * pattern    varchar(255)    null comment '模板 可以配合策略类使用',
 * strategy   varchar(255)    null comment '策略类',
 * cache_size int default 100 null comment '单次拉取缓存大小',
 */
public class IdPoolEntity {
    private long idCur;
    private long idMin;
    private long idMax;
    private String biz;
    private String pattern;
    private String strategy;
    private int cacheSize;

    public long getIdCur() {
        return idCur;
    }

    public void setIdCur(long idCur) {
        this.idCur = idCur;
    }

    public long getIdMin() {
        return idMin;
    }

    public void setIdMin(long idMin) {
        this.idMin = idMin;
    }

    public long getIdMax() {
        return idMax;
    }

    public void setIdMax(long idMax) {
        this.idMax = idMax;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }
}
