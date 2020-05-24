package com.zpark.dao;

import com.zpark.entity.Poem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PoemDao {
    //分页查询
    List<Poem> queryByPage(@Param("start")Integer start,@Param("size")Integer size);

    //查询全部
    List<Poem> queryAll();

    //查询一个
    Poem queryOnePoem(Poem poem);

    //查询总条数
    Integer count();

    //查询全部作者
    List<String> queryAuthor();

    //增加
    void addPoem(Poem poem);

    //删除
    void delPoem(Poem poem);

    //修改
    void updatePoem(Poem poem);
}
