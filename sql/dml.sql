-- auto-generated definition
create table id_pool
(
    biz    varchar(50)     not null comment '业务类型'
        primary key,
    id_cur     int             not null comment '当前id',
    id_min     int             not null comment '最小值id',
    id_max     int             not null comment '最大值id',
    pattern    varchar(255)    null comment '模板待定=》M20210709010110001',
    strategy   varchar(255)    null comment '策略类',
    cache_size int default 100 null comment '单次拉取缓存大小',
    load_factor double default 0.6 null comment '使用了60%的量后触发加载下一个号段'

)
    comment 'id生成辅助表';

create index idx_biz_idCur
    on id_generator (biz, id_cur);

