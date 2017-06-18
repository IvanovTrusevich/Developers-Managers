package by.itransition.config;

import by.itransition.data.model.User;
import by.itransition.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.CacheControl;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author Ilya Ivanov
 */
@Configuration
//@EnableWebMvc
@ComponentScan("by.itransition.web")
@ImportResource("classpath:elfinder-servlet.xml")
public class WebConfig extends WebMvcConfigurerAdapter {
    private UserService userService;

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");

        resolver.setViewClass(JstlView.class);
        // let you to expose all spring beans in jstl
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        // forward request for static resources to servlet's container default servlet
        configurer.enable();
    }


    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("/resources/messages/messages", "classpath:ValidationMessages");
        messageSource.setCacheSeconds(10);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public CookieLocaleResolver localeResolver(){
        CookieLocaleResolver localeResolver = new CookieLocaleResolver() {
            @Override
            public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
                super.setLocale(request, response, locale);
                final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal != null && principal instanceof User) {
                    User user = (User) principal;
                    if (!user.getLocale().equals(locale.toString())) {
                        final User oneWithCredentials = userService.findOneWithCredentials(user.getEmail());
                        oneWithCredentials.setLocale(locale.toString());
                        userService.saveRegisteredUser(oneWithCredentials);
                    }
                }
            }
        };
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        localeResolver.setCookieName("locale-cookie");
        localeResolver.setCookieMaxAge(3600);
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeInterceptor(){
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        interceptor.setIgnoreInvalidLocale(true);
        return interceptor;
    }

//    @Bean
//    public ThemeResolver themeResolver() {
//        final ThemeResolver themeResolver = new ThemeResolver();
//
//        return themeResolver;
//    }
//
//    @Bean
//    public ThemeChangeInterceptor themeInterceptor() {
//        final ThemeChangeInterceptor interceptor = new ThemeChangeInterceptor();
//        return interceptor;
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeInterceptor());
    }

    @Bean
    public FilterRegistrationBean registerOpenEntityManagerInViewFilterBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        OpenEntityManagerInViewFilter filter = new OpenEntityManagerInViewFilter();
        // TODO add reasonable url patterns to filter
        registrationBean.addUrlPatterns("/**");
        registrationBean.setFilter(filter);
        registrationBean.setOrder(5);
        return registrationBean;
    }
//
//    @Bean(name = "simpleMappingExceptionResolver")
//    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
//        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver() {
//            @Override
//            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//                return super.resolveException(request, response, handler, ex);
//            }
//
//            @Override
//            protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//                ModelAndView mav = super.doResolveException(request, response, handler, ex);
//                mav.addObject("url", request.getRequestURL());
//                return mav;
//            }
//        };
//        Properties mappings = new Properties();
//        // add mappings: customException -> custom exception view name
//        mappings.setProperty("DatabaseException", "databaseError");
//        r.setExceptionMappings(mappings);
//        r.setDefaultErrorView("error");
//        r.setWarnLogCategory(this.getClass().getName());
//        r.setOrder(Integer.MAX_VALUE);
//        return r;
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/res/**")
                .addResourceLocations("/resources/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS));
    }

    @Bean
    public MultipartResolver multipartResolver() throws IOException {
        return new StandardServletMultipartResolver();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/cloud").setViewName("cloud");
        registry.addViewController("/wiki").setViewName("wiki");
        registry.addRedirectViewController("/lost", "/login?lost=true");
        registry.addViewController("/recovery").setViewName("recovery");
        registry.addViewController("/project").setViewName("project");
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
