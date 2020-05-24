package com.zpark.es.repository;


import com.zpark.entity.Poem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PoemRepository extends ElasticsearchRepository<Poem,String> {
}
