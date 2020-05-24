package com.zpark.service.impl;

import com.zpark.dao.CategoryDao;
import com.zpark.entity.Category;
import com.zpark.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;
    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public List<Category> findAll() {
        return categoryDao.queryAll();
    }
}
