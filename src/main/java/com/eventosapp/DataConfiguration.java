package com.eventosapp;

import javax.activation.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


@Configuration
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class DataConfiguration {
	
	// esse bean configura o banco
	@Bean	
	public DataSource dataSource() {
		DriverManagerDataSource dataSource =new DriverManagerDataSource();
		
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/eventosapp");
		dataSource.setUsername("root");
        dataSource.setPassword("root");
		return dataSource();
	}
	
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter  adapter=new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);//permite q hibernate crie as tabelas autoamticamente
		adapter.setDatabasePlatform("org.hibernate.dialect.MYSQLDialect");
		adapter.setPrepareConnection(true);
		return adapter;
	}

}
