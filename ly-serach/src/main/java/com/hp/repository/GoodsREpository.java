package com.hp.repository;

import com.hp.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsREpository extends ElasticsearchRepository<Goods,Long> {
}
