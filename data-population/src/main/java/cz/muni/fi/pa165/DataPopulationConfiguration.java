package cz.muni.fi.pa165;

import cz.muni.fi.pa165.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

@Configuration
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {PopulationFacadeImpl.class})
public class DataPopulationConfiguration {

    @Autowired
    PopulationFacade populationFacade;

    @PostConstruct
    public void populate() {
        populationFacade.populate();
    }

}
