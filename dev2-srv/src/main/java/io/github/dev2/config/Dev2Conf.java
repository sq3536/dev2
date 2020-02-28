package io.github.dev2.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import io.github.dev2.config.liquibase.IProperties;
import liquibase.integration.spring.SpringLiquibase;
import io.github.dev2.config.liquibase.MasterProperties;
import io.github.dev2.config.liquibase.TempMasterProperties;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class Dev2Conf
{


    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

     /**
     * 多数据源切换
     * @return
     */
    @Bean
    public DatabaseIdProvider getDatabaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties p = new Properties();
        p.setProperty("Oracle", "oracle");
        p.setProperty("MySQL", "mysql");
        p.setProperty("DM", "oracle");//达梦数据库使用oracle模式
        p.setProperty("H2", "postgresql");//根据当前运行的数据库设置h2对应的databaseid
        p.setProperty("SQL Server", "sqlserver");
        p.setProperty("PostgreSQL", "postgresql");
        p.setProperty("DB2", "db2");
        databaseIdProvider.setProperties(p);
        return databaseIdProvider;
    }
   /**
     * 主数据源版本管理
     * @param
     * @return
     */
    @Bean
    public SpringLiquibase masterliquibase(MasterProperties masterProperties) {
        return LiquibaseInit(masterProperties);
    }
    /**
     * 临时数据源版本管理
     * @param
     * @return
     */
    @Bean
    public SpringLiquibase tempMasterliquibase(TempMasterProperties tempMasterProperties) {
        return LiquibaseInit(tempMasterProperties);
    }
    /**
     * liquibase初始化数据库
     * @param properties
     * @return
     */
   private SpringLiquibase LiquibaseInit(IProperties properties){
       DruidDataSource druidDataSource = new DruidDataSource();
       druidDataSource.setUsername(properties.getUsername());
       druidDataSource.setPassword(properties.getPassword());
       druidDataSource.setUrl(properties.getUrl());

       SpringLiquibase liquibase = new SpringLiquibase();
       liquibase.setDataSource(druidDataSource);
       liquibase.setChangeLog(getChangelog(properties.getIsSyncDBSchema(),properties.getConf()));
       liquibase.setContexts("development,test,production");
       liquibase.setShouldRun(true);
       liquibase.setDefaultSchema(properties.getDefaultSchema());
       return liquibase;
   }
    /**
     * 获取数据库差异文件
     * @param isSyncDBSchema  是否同步表结构
     * @param conf  //liquibase配置文件
     * @return
     */
    private String getChangelog(String isSyncDBSchema,String conf){
        String defaultChangelog="classpath:liquibase/empty.xml";

        if((!StringUtils.isEmpty(isSyncDBSchema))&&(!StringUtils.isEmpty(conf))){
            if(isSyncDBSchema.toLowerCase().equals("true"))
                defaultChangelog=conf;
        }
            return defaultChangelog;
    }
    /**
     * mybatis游标查询 MyBatisCursorItemReader
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public MyBatisCursorItemReader myMyBatisCursorItemReader(SqlSessionFactory sqlSessionFactory) {
        MyBatisCursorItemReader reader =new MyBatisCursorItemReader();
        reader.setQueryId("");
        reader.setSqlSessionFactory(sqlSessionFactory);
        return reader;
    }


    @Value("${dev2.async.poolsize:2}")
    private Integer poolsize;

    @Bean("asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolsize);
        executor.setMaxPoolSize(poolsize);
        executor.setQueueCapacity(2000);
        executor.setKeepAliveSeconds(600);
        executor.setThreadNamePrefix("asyncExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}