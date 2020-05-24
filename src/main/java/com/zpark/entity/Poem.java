package com.zpark.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
@Document(indexName = "poem",type = "poem")
public class Poem implements Serializable {
  @Id
  private String id;
  @Field(type = FieldType.Text,analyzer = "ik_max_word")
  private String name;
  @Field(type = FieldType.Keyword)
  private String author;
  @Field(type = FieldType.Keyword)
  private String type;
  @Field(type = FieldType.Text,analyzer = "ik_max_word")
  private String content;
  @Field(type = FieldType.Keyword)
  private String href;
  @Field(type = FieldType.Text,analyzer = "ik_max_word")
  private String authordes;
  @Field(type = FieldType.Keyword)
  private String origin;
  @Field(type = FieldType.Keyword)
  private String imagePath;
  @Field(type = FieldType.Nested)
  private Category category = new Category();
  private List<Comment> comments;

}
