package net.telesync.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "net.telesync.web")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver configureViewResolvers(ContentNegotiationManager manager) {
        List<ViewResolver> resolvers = new ArrayList<>();

        InternalResourceViewResolver jspResolver = new InternalResourceViewResolver();
        jspResolver.setPrefix("/WEB-INF/views/");
        jspResolver.setSuffix(".jsp");
        jspResolver.setViewClass(JstlView.class);

        resolvers.add(new ViewResolver() {
            @Override
            public View resolveViewName(String s, Locale locale) throws Exception {
                return new MappingJackson2JsonView();
            }
        });
        resolvers.add(jspResolver);

        ContentNegotiatingViewResolver mainResolver = new ContentNegotiatingViewResolver();
        mainResolver.setViewResolvers(resolvers);
        mainResolver.setContentNegotiationManager(manager);
        return mainResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
        registry.addResourceHandler("/tpl/**").addResourceLocations("/tpl/");
    }
}
