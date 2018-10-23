package com.epam.esm.task5.repository.impl;

import com.epam.esm.task5.repository.BasicRepositoryImpl;
import com.epam.esm.task5.repository.TagRepository;
import com.epam.esm.task5.repository.entity.Tag;
import org.springframework.stereotype.Repository;

@Repository
public class TagRepositoryImpl extends BasicRepositoryImpl<Tag> implements TagRepository {

}
