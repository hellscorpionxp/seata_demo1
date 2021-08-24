package com.tony.conf;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DBConfiguration {

  @Bean
  @Primary
  @ConfigurationProperties("spring.datasource")
  public DataSource orderDS() {
    return new DruidDataSource();
  }

  @Bean
  public JdbcTemplate orderJdbcTemplate(DataSource ds) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
    jdbcTemplate.execute("truncate table order_tbl");
    return jdbcTemplate;
  }

}
