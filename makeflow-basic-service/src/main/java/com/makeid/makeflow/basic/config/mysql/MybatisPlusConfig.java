package com.makeid.makeflow.basic.config.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

/**
 * MybatisPlus配置
 *
 * @author huayangchen
 * @create 2020-07-20 20:19
 **/
@ConditionalOnProperty(prefix = "makeflow.data",name = "dataType",havingValue = "mysql")
@EnableTransactionManagement
@Configuration
@MapperScan("com.makeid.makeflow.**.mapper")
public class MybatisPlusConfig {

    @Bean
    public CustomizedSqlInjector customizedSqlInjector() {
        return new CustomizedSqlInjector();
    }

    @Bean(initMethod = "init",destroyMethod = "close")
    @ConfigurationProperties(prefix = "makeflow.data.mysql.druid-data-source")
    @ConditionalOnProperty(name = "makeflow.data.mysql.druid-data-source.type", havingValue = "com.alibaba.druid.pool.DruidDataSource")
    public DruidDataSource dataSource() {
        return new DruidDataSource();
    }



    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        // 配置扫面路径
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath*:com/makeid/makeflow/**/mapper/xml/*.xml"));
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        // 分页插件
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setLogImpl(StdOutImpl.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        configuration.setCallSettersOnNulls(true);
        configuration.addInterceptor(mybatisPlusInterceptor);
        GlobalConfig globalConfig = GlobalConfigUtils.defaults();
        globalConfig.setSqlInjector(customizedSqlInjector());
        configuration.setGlobalConfig(globalConfig);
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        //Cause: java.lang.IllegalStateException: Type handler was null on parameter mapping for property '__frch_item_0.variables'. It was either not specified and/or could not be found for the javaType (java.util.Map) : jdbcType (null) combination.
        typeHandlerRegistry.register(Map.class, JacksonTypeHandler.class);

        sqlSessionFactory.setGlobalConfig(globalConfig);
        sqlSessionFactory.setConfiguration(configuration);
        return sqlSessionFactory.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
