package com.hp.page.service;
import com.hp.page.client.GoodsClient;
import com.hp.page.client.SpecClient;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.pojo.Spu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PageService {
    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecClient specClient;
    public Map<String,Object> loadData(Long spuId) {
        Map<String,Object> map=new HashMap<String,Object>();
        Spu spu=goodsClient.querySpuById(spuId);
        //查询spu
        map.put("spu",spu);
        //查询spudetail
        map.put("spuDetail",goodsClient.querySpuDetailBySpuId(spuId));
        //查询skus
        map.put("skus",goodsClient.querySkuBySpuId(spuId));
        //查询规格参数
        List<SpecParam> specParamList = this.specClient.querySpecParam(null, spu.getCid3(), null, null);

        Map<Long,Object> specMap=new HashMap<Long,Object>();
        for(SpecParam specParam:specParamList){

            specMap.put(specParam.getId(),specParam.getName());
        }
        //放入
        map.put("specParams",specMap);
        //specGroups
        List<SpecGroup> groupList = specClient.querySpecGroups(spu.getCid3());
        map.put("specGroups",groupList);
        return map;
    }
    }
