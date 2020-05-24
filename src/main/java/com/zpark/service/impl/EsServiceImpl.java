package com.zpark.service.impl;

import com.zpark.dao.PoemDao;
import com.zpark.entity.Category;
import com.zpark.entity.Poem;
import com.zpark.es.repository.PoemRepository;
import com.zpark.service.EsService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class EsServiceImpl implements EsService {

    @Autowired
    private PoemRepository poemRepository;

    @Autowired
    private PoemDao poemDao;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Override
    public Map<String,Object> flushIndex() {
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            elasticsearchTemplate.deleteIndex(Poem.class);
            elasticsearchTemplate.putMapping(Poem.class);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @Override
    public Map<String, Object> createIndex() {
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            List<Poem> poems = poemDao.queryAll();
            poemRepository.saveAll(poems);
            map.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    @Override
    public  List<Poem> findByKeywords(String content, String type, String author) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        QueryBuilder queryBuilder;
        //检索框是否为空
        if(StringUtils.isEmpty(content)){
            queryBuilder = QueryBuilders.matchAllQuery();
            nativeSearchQueryBuilder.withQuery(queryBuilder);
        }else{
            //设置高亮查询
            HighlightBuilder.Field highlightBuilder = new HighlightBuilder.Field("*")
                    .preTags("<span style='color:red'>")
                    .postTags("</span>")
                    .requireFieldMatch(false);
            //多字段分词查询
            queryBuilder = QueryBuilders.queryStringQuery(content).analyzer("ik_max_word")
                    .field("name").field("content").field("type").field("author");
            nativeSearchQueryBuilder.withQuery(queryBuilder);
            nativeSearchQueryBuilder.withHighlightFields(highlightBuilder);
        }

        //类别是否为空  bool查询 过滤查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        List<QueryBuilder> filter = boolQueryBuilder.filter();
        if(!StringUtils.isEmpty(type)){
            filter.add(QueryBuilders.termQuery("type",type));
        }

        //是否根据作者查询
        if(!StringUtils.isEmpty(author)){
            filter.add(QueryBuilders.termQuery("author",author));
        }

        NativeSearchQuery nativeSearchQuery = nativeSearchQueryBuilder.withPageable(PageRequest.of(0, 20))
                .withFilter(boolQueryBuilder)
                .build();

        AggregatedPage<Poem> poems = elasticsearchTemplate.queryForPage(nativeSearchQuery, Poem.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                SearchHits responseHits = response.getHits();
                SearchHit[] searchHits = responseHits.getHits();
                List<Poem> poems = new ArrayList<>();
                for (SearchHit searchHit : searchHits) {
                    //全部
                    Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                    //高亮部分
                    Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                    Poem poem = new Poem();
                    poem.setId(searchHit.getId());
                    poem.setOrigin(sourceAsMap.get("origin").toString());
                    poem.setHref(sourceAsMap.get("href").toString());
                    poem.setImagePath(sourceAsMap.get("imagePath").toString());

                    poem.setName(sourceAsMap.get("name").toString());
                    if (highlightFields.containsKey("name")) {
                        poem.setName(highlightFields.get("name").fragments()[0].toString());
                    }

                    poem.setAuthor(sourceAsMap.get("author").toString());
                    if (highlightFields.containsKey("author")) {
                        poem.setAuthor(highlightFields.get("author").fragments()[0].toString());
                    }

                    poem.setAuthordes(sourceAsMap.get("authordes").toString());
                    if (highlightFields.containsKey("authordes")) {
                        poem.setAuthordes(highlightFields.get("authordes").fragments()[0].toString());
                    }

                    String cate = sourceAsMap.get("category").toString();
                    if (highlightFields.containsKey("category")) {
                        cate = highlightFields.get("category").fragments()[0].toString();
                    }
                    poem.setCategory(getObject(cate));

                    poem.setContent(sourceAsMap.get("content").toString());
                    if (highlightFields.containsKey("content")) {
                        poem.setContent(highlightFields.get("content").fragments()[0].toString());
                    }

                    poem.setType(sourceAsMap.get("type").toString());
                    if (highlightFields.containsKey("type")) {
                        poem.setType(highlightFields.get("type").fragments()[0].toString());
                    }
                    poems.add(poem);
                }
                return new AggregatedPageImpl<T>((List<T>) poems);
            }
        });
        return poems.getContent();
    }
    @Override
    public Map<String,Object> delAll() {
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            poemRepository.deleteAll();
            map.put("success",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
        }
        return map;
    }

    public Category getObject(String cate){
        Category category = new Category();
        String[] split = cate.split(",");
        String[] split1 = split[0].split("=");
        category.setName(split1[1]);
        return category;
    }
}
