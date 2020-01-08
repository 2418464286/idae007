package com.bigdata;

import com.hp.LyserachApplication;
import com.hp.client.GoodsClient;
import com.hp.pojo.Goods;
import com.hp.repository.GoodsREpository;
import com.hp.service.IndexService;
import com.leyou.item.bo.SpuBo;
import com.leyou.po.PageResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LyserachApplication.class)
public class IndexText {
@Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
@Autowired
private GoodsREpository goodsREpository;
@Autowired
    private GoodsClient goodsClient;
    @Autowired
    private IndexService indexService;
    @Test
    public void  init(){

    elasticsearchTemplate.createIndex(Goods.class);
    elasticsearchTemplate.putMapping(Goods.class);
}
@Test
public void loadDate(){
    int page=1;
    while(true){
      PageResult<SpuBo> pageResult=goodsClient.querySpuByPage(null,null,page,50);
    //为空
      if (pageResult==null){
        break;
    }
    page++;
    List<SpuBo> items=pageResult.getItems();
    List<Goods> list=new ArrayList<Goods>();
    for (SpuBo spuBo:items){
       Goods goods=indexService.buildGoods(spuBo);
       list.add(goods);
    }
    goodsREpository.saveAll(list);
    }

}
}
