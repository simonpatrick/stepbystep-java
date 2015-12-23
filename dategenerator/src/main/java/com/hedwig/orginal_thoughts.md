# Data Generator

在自动化测试过程中有的时候构建很多的数据很麻烦,所以在想有没有什么比较好的办法来可以自动生成一些测试数据.
不同的业务流程需要不同的业务数据. 如何做呢?

## 初步想法
一般测试数据都定义为String类型

### 根据变量名自动生成
这个思路的需要整理:
- 特征变量名, 对应的一系列的枚举值,这个枚举值需要可以维护,同时有命名空间(子系统)的概念

### 根据注解说明来自动生成
- 注解定义
- 默认注解策略


### 资源
- https://github.com/benas/jPopulator.git
- https://github.com/groupon/retromock.git
- https://github.com/Codearte/jfairy.git
- https://github.com/celiovasconcelos/jpamock.git