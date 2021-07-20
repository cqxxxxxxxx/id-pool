-- auto-generated definition
create table id_pool
(
    id_cur      int                not null comment '当前id'
        primary key,
    id_min      int                not null,
    id_max      int                not null,
    biz         varchar(50)        null comment '业务类型',
    pattern     varchar(255)       null comment '模板 可以配合策略类使用',
    strategy    varchar(255)       null comment '策略类',
    cache_size  int    default 100 null comment '单次拉取缓存大小',
    load_factor double default 0.6 null comment '使用了60%的量后触发加载下一个号段',
    constraint id_generator_id_max_uindex
        unique (id_max),
    constraint id_generator_id_min_uindex
        unique (id_min)
)
    comment 'id生成辅助表';
