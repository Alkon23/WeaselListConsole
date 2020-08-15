/*
 * WeaselList Console. Copyright (c) 2020.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
