/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sh.connection.persistence.jpa.repository;

import com.sh.connection.persistence.interfaces.UserDao;
import com.sh.connection.persistence.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends UserDao, CrudRepository<User, Long> {

    public User findByLogin(String login);

    public User findByEmail(String email);
}
