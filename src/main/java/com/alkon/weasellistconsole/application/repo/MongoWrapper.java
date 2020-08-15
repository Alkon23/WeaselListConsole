package com.alkon.weasellistconsole.application.repo;

import com.alkon.weasellistconsole.application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MongoWrapper {

    @Autowired
    private WeaselRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    public boolean usernameExists(@NonNull final String username) {
        return repository.existsUserByNick(username);
    }

    public boolean emailExists(@NonNull final String email) {
        return repository.existsUserByEmail(email);
    }

    public User getUser(@NonNull final String nickname, @NonNull final String password) {
        final User user = repository.findUserByNick(nickname);
        if (user == null) {
            return null;
        }
        return encoder.matches(password, user.getPassword()) ? user : null;
    }

    public User registerUser(@NonNull final User user) {
        user.setPassword(this.encoder.encode(user.getPassword()));
        return this.saveUser(user);
    }

    public User saveUser(@NonNull final User user) {
        return repository.save(user);
    }

    public User saveUserAndPass(@NonNull final User user) {
        user.setPassword(this.encoder.encode(user.getPassword()));
        return repository.save(user);
    }

}
