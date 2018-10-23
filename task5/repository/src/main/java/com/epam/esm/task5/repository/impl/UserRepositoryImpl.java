package com.epam.esm.task5.repository.impl;

import com.epam.esm.task5.repository.BasicRepositoryImpl;
import com.epam.esm.task5.repository.UserRepository;
import com.epam.esm.task5.repository.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends BasicRepositoryImpl<User> implements UserRepository {

}
