package com.hp.service;

import com.hp.pojo.Goods;
import com.hp.repository.GoodsREpository;
import com.hp.utils.SearchRequest;
import com.leyou.po.PageResult;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    @Autowired
    private GoodsREpository goodsREpository;


    public PageResult<Goods> search(SearchRequest searchRequest) {
        //用户搜索关键字
        String key = searchRequest.getKey();
        //第几页
        Integer page = searchRequest.getPage();
        //创建查询对象NativeSearchQueryBuilder
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //查询条件
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("all",key).operator(Operator.AND));
        //分页
        nativeSearchQueryBuilder.withPageable(PageRequest.of(page-1,searchRequest.getSize()));
       //过滤
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id","skus","subTitle"},null));
        //搜索
        Page<Goods> goodsPage = goodsREpository.search(nativeSearchQueryBuilder.build());


        return new PageResult<>(goodsPage.getTotalElements(),new Long(goodsPage.getTotalPages()),goodsPage.getContent());
    }
}
