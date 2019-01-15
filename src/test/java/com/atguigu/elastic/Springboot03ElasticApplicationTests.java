package com.atguigu.elastic;

import com.atguigu.elastic.bean.Article;
import io.searchbox.client.JestClient;


import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot03ElasticApplicationTests {

    @Autowired
    JestClient jestClient;
    @Test
    public void contextLoads() {
        //1、给索引保存一个文档
        Article article = new Article();
        article.setId(1);
        article.setTitle("111");
        article.setAuthor("haha");
        article.setContent("Hello World");

        Index index = new Index.Builder(article).index("atguigu").type("news").build();
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void search() throws IOException {
        //查询表达式
        String json="{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"content\" : \"hello\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        //构建搜索
        Search search = new Search.Builder(json).addIndex("atguigu").addType("news").build();

        SearchResult result = jestClient.execute(search);
        System.out.println(result.getJsonString());
    }

}

