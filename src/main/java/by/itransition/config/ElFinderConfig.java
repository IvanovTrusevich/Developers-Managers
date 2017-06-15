package by.itransition.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by User on 14.06.2017.
 */
@Configuration
@ComponentScan({"cn.bluejoe.elfinder"})
@ImportResource("classpath:elfinder-servlet.xml")
public class ElFinderConfig {


}
