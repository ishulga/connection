package com.sh.connection;

import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
@ComponentScan(basePackageClasses = {ApplicationConfig.class})
public class ApplicationConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "connection";
    }

    @Override
    public Mongo mongo() throws Exception {
        Mongo mongo = new Mongo();
        mongo.setWriteConcern(WriteConcern.SAFE);
        return mongo;
    }

}
