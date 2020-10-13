package org.step.linked.step.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan
public class EncodeConfig {

    @Configuration
    private static class InnerConfig {

        public InnerConfig() {
        }

        @Bean
        A a() {
            return new A();
        }

        @Bean
        B b() {
            return new B();
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder(InnerConfig innerConfig) {
        final int strength = 8;

        A a = innerConfig.a();
        B b = innerConfig.b();

        return new BCryptPasswordEncoder(strength);
    }

    private static class A {

    }

    private static class B {

    }
}
