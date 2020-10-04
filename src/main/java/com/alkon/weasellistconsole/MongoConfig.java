package com.alkon.weasellistconsole;

import com.alkon.weasellistconsole.application.ApplicationContext;
import com.alkon.weasellistconsole.application.repo.WeaselRepository;
import com.mongodb.ConnectionString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static com.alkon.weasellistconsole.application.PropertyFile.MONGO_URL;

@Configuration
@EnableMongoRepositories(basePackageClasses = WeaselRepository.class)
public class MongoConfig {

    @Bean
    public MongoDatabaseFactory mongoDbFactory() {
        String uri = (String) ApplicationContext.getParam(MONGO_URL);
        uri = uri == null ? "mongodb://localhost:27017/weaseldb" : uri;

        return new SimpleMongoClientDatabaseFactory(new ConnectionString(uri));
    }

}
