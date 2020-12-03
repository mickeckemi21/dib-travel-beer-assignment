package io.mickeckemi21.beerassignment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync(proxyTargetClass = true)
@Configuration(proxyBeanMethods = false)
public class AsyncConfig {
}
