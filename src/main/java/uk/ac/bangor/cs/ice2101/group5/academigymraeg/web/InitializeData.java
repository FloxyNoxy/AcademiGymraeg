package uk.ac.bangor.cs.ice2101.group5.academigymraeg.web;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import uk.ac.bangor.cs.ice2101.group5.academigymraeg.repository.NounRequestRepository;


/**
 * https://stackoverflow.com/questions/39280340/how-to-run-sql-scripts-and-get-data-on-application-startup
 * @author owenw
 *
 */
@Component
public class InitializeData {

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private NounRequestRepository repo;

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
    	if(repo.count() == 0) {
    		Logger LOG = LoggerFactory.getLogger(InitializeData.class);
    		LOG.warn("Nouns table is empty");
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("data.sql"));
            resourceDatabasePopulator.execute(dataSource);
            LOG.warn("Nouns table has been populated");
    	}
    	

    }
    
}
