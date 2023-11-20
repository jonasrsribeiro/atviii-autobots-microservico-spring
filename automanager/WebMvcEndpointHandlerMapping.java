import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties.MatchingStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* http://localhost:8080/swagger-ui/ */

@Configuration
public class WebMvcConfig {

    @Autowired
    private WebEndpointsSupplier webEndpointsSupplier;

    @Autowired
    private ServletEndpointsSupplier servletEndpointsSupplier;

    @Autowired
    private ControllerEndpointsSupplier controllerEndpointsSupplier;

    @Autowired
    private EndpointMediaTypes endpointMediaTypes;

    @Autowired
    private CorsEndpointProperties corsProperties;

    @Autowired
    private WebEndpointProperties webEndpointProperties;

    @Autowired
    private Environment environment;

    @Bean
    public AbstractHandlerMapping webEndpointServletHandlerMapping() {
        List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
        allEndpoints.addAll(webEndpoints);
        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());

        String basePath = webEndpointProperties.getBasePath();
        EndpointMapping endpointMapping = new EndpointMapping(new PathPatternParser(), basePath);

        boolean shouldRegisterLinksMapping = shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);

        MatchingStrategy matchingStrategy = WebMvcProperties.MatchingStrategy.ANT_PATH_MATCHER;

        return new WebMvcEndpointHandlerMapping(
                endpointMapping,
                webEndpoints,
                endpointMediaTypes,
                corsProperties.toCorsConfiguration(),
                new EndpointLinksResolver(allEndpoints, basePath),
                shouldRegisterLinksMapping,
                matchingStrategy
        );
    }

    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment,
                                               String basePath) {
        return webEndpointProperties.getDiscovery().isEnabled() &&
                (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
    }
}
