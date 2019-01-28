package com.hiyouka.sources.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


//@Component
public class MyBatisConfig{

    /**
     * sqlsessionfacotry 装配
     */
        @Configuration
        @MapperScan(basePackages = "com.jy.mapper.readsource" , sqlSessionFactoryRef = "readSourceSession")
        public class ReadSourceMyBatisConfig {

            @Qualifier("readsource")
            @Autowired
            private DataSource ds;

            @Bean("readSourceSession")
            public SqlSessionFactory sqlSessionFactory() throws Exception {
                SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
                sqlSessionFactoryBean.setDataSource(ds);
    //        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/jy/mapper/readsource/**/*.xml"));
                return sqlSessionFactoryBean.getObject();
            }

            @Bean
            public SqlSessionTemplate sqlSessionTemplate() throws Exception {
                SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory()); // 使用上面配置的Factory
                return template;
            }
        }


    @Configuration
    @MapperScan(basePackages = "com.jy.mapper.master" , sqlSessionFactoryRef = "masterSession")
    public class MasterMyBatisConfig{

        @Qualifier("master")
        @Autowired
        private DataSource ds;

        @Bean("masterSession")
        public SqlSessionFactory sqlSessionFactory() throws Exception {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(ds);
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/jy/mapper/user/**/*.xml"));
            return sqlSessionFactoryBean.getObject();
        }

        @Bean
        public SqlSessionTemplate sqlSessionTemplate() throws Exception {
            SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory()); // 使用上面配置的Factory
            return template;
        }
    }

}

