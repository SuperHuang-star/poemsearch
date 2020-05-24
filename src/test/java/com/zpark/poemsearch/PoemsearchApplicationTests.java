package com.zpark.poemsearch;


import com.zpark.PoemsearchApplication;
import com.zpark.dao.CategoryDao;
import com.zpark.dao.PoemDao;
import com.zpark.entity.Category;
import com.zpark.entity.Poem;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = PoemsearchApplication.class)
@RunWith(SpringRunner.class)
public class PoemsearchApplicationTests {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private PoemDao poemDao;

    @Test
    public void poemQueryByPage() {
        List<Poem> poems = poemDao.queryByPage(1, 5);
        System.out.println(poems);
    }
    @Test
    public void poemQueryAll() {
        List<Poem> poems = poemDao.queryAll();
        System.out.println(poems);
    }
    @Test
    public void poemCount() {
        Integer poems = poemDao.count();
        System.out.println(poems);
    }

    @Test
    public void   findAll(){
        List<Category> categories = categoryDao.queryAll();
        System.out.println(categories);
    }

   /* @Test
    public void esCreateIndex() {
        List<Poem> poems = poemDao.queryByPage(1, 5);
        System.out.println(poems);

    }
    @Test
    public void esFlushIndex() {
        List<Poem> poems = poemDao.queryByPage(1, 5);
        System.out.println(poems);
    }
    @Test
    public void esDelAll() {
        List<Poem> poems = poemDao.queryByPage(1, 5);
        System.out.println(poems);
    }
    @Test
    public void esSearchByKeywords() {
        List<Poem> poems = poemDao.queryByPage(1, 5);
        System.out.println(poems);
    }*/

   @Test
    public void test1(){
       Md5Hash md5 = new Md5Hash("456123", "hnsd", 1024);
       System.out.println(md5.toHex());
   }
}
