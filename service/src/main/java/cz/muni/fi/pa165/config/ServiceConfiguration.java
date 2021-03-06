package cz.muni.fi.pa165.config;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.facade.StudyGroupFacadeImpl;
import cz.muni.fi.pa165.service.StudyGroupServiceImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Collections;

@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackageClasses={StudyGroupServiceImpl.class, StudyGroupFacadeImpl.class})
public class ServiceConfiguration {

    @Bean
    public Mapper dozer(){
        DozerBeanMapper beanMapper = new DozerBeanMapper();
        beanMapper.setMappingFiles(Collections.singletonList("dozerJdk8Converters.xml"));
        return beanMapper;
    }
}
