package com.example.demo

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment

@Configuration
@Profile("!test")
class FlywayConfig(private val env: Environment) {

  @Bean(initMethod = "migrate")
  fun flyway(): Flyway {
    return Flyway(
      Flyway.configure()
        .baselineOnMigrate(false)
        .cleanOnValidationError(env.getProperty("spring.flyway.clean-on-validation-error", "false").toBoolean())
        .locations(*env.getProperty("spring.flyway.locations")
          ?.split(",")!!
          .map { it.trim() }
          .toTypedArray())
        .dataSource(
          env.getRequiredProperty("spring.flyway.url"),
          env.getRequiredProperty("spring.flyway.user"),
          env.getRequiredProperty("spring.flyway.password")
        )
    )
  }
}
