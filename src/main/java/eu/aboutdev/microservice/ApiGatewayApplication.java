package eu.aboutdev.microservice;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.util.Optional;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.just("1");
    }

    @Bean
    @ConditionalOnClass(name = "io.opentelemetry.javaagent.OpenTelemetryAgent")
    public MeterRegistry otelRegistry() {
        Optional<MeterRegistry> otelRegistry = Metrics.globalRegistry.getRegistries().stream()
                .filter(r -> r.getClass().getName().contains("OpenTelemetryMeterRegistry"))
                .findAny();
        otelRegistry.ifPresent(Metrics.globalRegistry::remove);
        return otelRegistry.orElse(null);
    }
}
