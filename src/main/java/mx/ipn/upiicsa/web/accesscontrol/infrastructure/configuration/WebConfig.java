package mx.ipn.upiicsa.web.accesscontrol.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@lombok.RequiredArgsConstructor
public class WebConfig implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    private final @org.springframework.lang.NonNull mx.ipn.upiicsa.web.accesscontrol.infrastructure.security.SecurityInterceptor securityInterceptor;

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA: ");
        return filter;
    }

    @Override
    public void addInterceptors(
            @org.springframework.lang.NonNull org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor);
    }
}
