package com.alkon.weasellistconsole;

import com.alkon.weasellistconsole.application.repo.WeaselRepository;
import com.alkon.weasellistconsole.cli.commands.Command;
import com.alkon.weasellistconsole.cli.commands.Mongo;
import com.mongodb.ConnectionString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.*;

@Configuration
@EnableMongoRepositories(basePackageClasses = WeaselRepository.class)
public class MongoConfig {

    @Bean
    public MongoDatabaseFactory mongoDbFactory() {
        String uri = "mongodb://localhost:27017/weaseldb";
        try {
            File weasellist = new File("C:\\weasellist");
            File properties = new File("C:\\weasellist\\weasellist.properties");

            if (!weasellist.exists()) {
                weasellist.mkdir();
                new Mongo().execute(null, null);
            } else if (!properties.exists()) {
                new Mongo().execute(null, null);
            }

            BufferedReader reader = new BufferedReader(new FileReader(properties));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();

            uri = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new SimpleMongoClientDatabaseFactory(new ConnectionString(uri));
    }

}
