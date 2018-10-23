package com.epam.esm.task5.repository;

import com.epam.esm.task5.repository.impl.QuestRepositoryImpl;
import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Profile("test")
public class TestDataConfig {

  @Bean
  public DataSource dataSource() throws IOException, SQLException {
    FlywayPreparer preparer = FlywayPreparer.forClasspathLocation("db");
    EmbeddedPostgres embeddedPostgres = EmbeddedPostgres.start();
    DataSource dataSource = embeddedPostgres.getPostgresDatabase();
    preparer.prepare(dataSource);
    return dataSource;
  }

  @Bean(name = "txManager")
  public PlatformTransactionManager txManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory()
      throws IOException, SQLException {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource());
    em.setPackagesToScan("com.epam.esm.task5.repository");
    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(getHibernateProperties());
    return em;
  }

  private Properties getHibernateProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    properties.put("hibernate.show_sql", "true");
    properties.put("hibernate.hbm2ddl.auto", "update");
    properties.put("hibernate.generate_statistics", "true");
    return properties;
  }

  @Bean
  public QuestRepository orderRepository() {
    return new QuestRepositoryImpl();
  }

  @Bean
  public AuditorAware auditorAware() {
    return () -> 1L;
  }
}
