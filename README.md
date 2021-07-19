# ID POOL
id池，用于获取唯一id。目前支持基于号段模式生成, 以及支持各种ID包装处理

### USAGE
1. 执行sql，生成辅助表
2. 参考test，创建datasource，初始化IdPool
3. 调用IdPool.nextId()获取id，进行使用

### TODO
1. 完善各种常用的包装处理类
2. 完善测试
3. 基于snowflake的id pool实现
4. 完善异常处理逻辑
5. 集成spring-boot自动化配置