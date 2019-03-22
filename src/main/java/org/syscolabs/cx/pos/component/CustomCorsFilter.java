package org.syscolabs.cx.pos.component;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class CustomCorsFilter extends CorsFilter {

    public CustomCorsFilter(CorsConfigurationSource configSource) {
        super(configSource);
    }

}