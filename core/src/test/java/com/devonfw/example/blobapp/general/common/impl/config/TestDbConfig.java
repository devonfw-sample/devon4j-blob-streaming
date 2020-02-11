package com.devonfw.example.blobapp.general.common.impl.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.devonfw.module.test.common.base.clean.TestCleaner;
import com.devonfw.module.test.common.base.clean.TestCleanerImpl;
import com.devonfw.module.test.common.base.clean.TestCleanerPlugin;
import com.devonfw.module.test.common.base.clean.TestCleanerPluginFlyway;
import com.devonfw.module.test.common.base.clean.TestCleanerPluginNone;

/**
 * {@link Configuration} for Database in JUnit tests.
 */
@Configuration
public class TestDbConfig {

  /**
   * @return the {@link TestCleaner}.
   */
  @Bean
  public TestCleaner testCleaner() {

    return new TestCleanerImpl();
  }

  /**
   * @return the {@link TestCleanerPluginFlyway}.
   */
  @Bean
  @ConditionalOnBean(Flyway.class)
  public TestCleanerPlugin testCleanerPluginFlyway() {

    return new TestCleanerPluginFlyway();
  }

  @Bean
  @ConditionalOnMissingBean(TestCleanerPlugin.class)
  public TestCleanerPlugin testCleanerPluginNone() {

    return new TestCleanerPluginNone();
  }

}
