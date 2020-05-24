package com.zpark.service.impl;

import com.zpark.dao.PoemDao;
import com.zpark.entity.Poem;
import com.zpark.service.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class PoemServiceImpl implements PoemService {

    @Autowired
    private PoemDao poemDao;



    @Override
    @Transactional(propagation= Propagation.SUPPORTS)
    public Map<String,Object> queryByPage(Integer page, Integer rows) {
        Map<String,Object> map = new HashMap<String,Object>();
        Integer records = poemDao.count();
        Integer total = records%rows==0?records/rows:records/rows+1;
        Integer start  = (page-1)*rows;
        List<Poem> poems = poemDao.queryByPage(start, rows);
        map.put("page",page);
        map.put("rows",poems);
        map.put("total",total);
        map.put("records",records);
        return map;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public Integer count() {
        return poemDao.count();
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public Set<String> queryAuthor() {
        Set<String> objects = new HashSet<>();
        List<String> strings = poemDao.queryAuthor();
        for (String string : strings) {
            objects.add(string);
        }
        return objects;
    }

    @Override
    @Transactional(propagation=Propagation.SUPPORTS)
    public Poem findOne(Poem poem) {
        return poemDao.queryOnePoem(poem);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public void addPoem(Poem poem) {
        poemDao.addPoem(poem);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public void delPoem(Poem poem) {
        poemDao.delPoem(poem);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED)
    public void updatePoem(Poem poem) {
        poemDao.updatePoem(poem);
    }
}
