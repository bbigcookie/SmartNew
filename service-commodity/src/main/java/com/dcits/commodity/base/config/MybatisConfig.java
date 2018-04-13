package com.dcits.commodity.base.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.dcits.commodity.base.interceptor.PaginationInterceptor;


@Configuration
@AutoConfigureAfter({ DataSourceConfiguration.class })
@EnableTransactionManagement
public class MybatisConfig implements TransactionManagementConfigurer {

	@Value("${mybatis.dialect}")
	private String dialect;
	@Value("${mybatis.mapper-locations}")
	private String mapperLocations;

	private Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

	private RelaxedPropertyResolver propertyResolver;

	@Resource(name="dataSource")
	DataSource dataSource;

	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() {
		try {
			SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
			sessionFactory.setDataSource(dataSource);
			Properties properties = new Properties();
			properties.setProperty("dialect",dialect);
			sessionFactory.setConfigurationProperties(properties);
			sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
			Interceptor[] plugins = new Interceptor[]{new PaginationInterceptor()};
			sessionFactory.setPlugins(plugins);
			return sessionFactory.getObject();
		} catch (Exception e) {
			logger.warn("Could not confiure mybatis session factory");
			logger.error(e.getMessage(),e);
			return null;
		}
	}

	@Bean(name="sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name="transactionManager")
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

}
