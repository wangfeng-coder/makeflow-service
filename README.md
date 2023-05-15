# makeflow-service
>   打造国内最强工作流
*  可单独作为审批工作流中台服务
*  可打包到工程中做为审批工作流模块

### 工程模块介绍
如何拆分：
 *  功能 : 便于服务扩容，业务逻辑理解
 * 数据库 : 此处将数据库操作单独提出来，防止以后对应模板作为服务的时候，集群数量限制于数据库本身连接池
以及便于数据源类型的切换不影响原有逻辑。

 模块名|描述
--- | :---:
makeflow-form-api|表单服务的api，用于其他模块服或务***调用***
makeflow-form-data|表单服务单纯对***数据库***的操作
makeflow-form-servie|表单服务(核心***逻辑***)
makeflow-template-api|模板服务的api，用于其他模块或服务***调用***
makeflow-template-data|模板服务单纯对***数据库***的操作
makeflow-template-service|模板服务(核心***逻辑***)
makeflow-workflow-api|工作流引擎的api，用于其他模块或服务***调用***
makeflow-workflow-data|工作流引擎单纯对***数据库***的操作
makeflow-workflow-service|工作量引擎服务(核心***逻辑***)
makeflow-service-application|springboot启动的地方




