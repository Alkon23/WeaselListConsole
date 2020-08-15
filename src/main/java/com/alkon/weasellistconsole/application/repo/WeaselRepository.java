package com.alkon.weasellistconsole.application.repo;

import com.alkon.weasellistconsole.application.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaselRepository extends MongoRepository<User, String> {

    User findUserByNick(String nick);

    boolean existsUserByNick(String nick);

    boolean existsUserByEmail(String email);

}
