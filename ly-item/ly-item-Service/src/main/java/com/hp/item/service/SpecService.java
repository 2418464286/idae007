package com.hp.item.service;

import com.hp.item.mapper.SpecGroupMapper;
import com.hp.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecService {
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private  SpecParamMapper specParamMapper;

    public List<SpecGroup> querySpecGroup(Long cid) {
        SpecGroup specGroup=new SpecGroup();
        specGroup.setCid(cid);
        //通过分类的id查询规格参数
        List<SpecGroup> specGroupList=specGroupMapper.select(specGroup);
       //查询规格参数里的规格组
        specGroupList.forEach(t->{
            Long id=t.getId();
            SpecParam specParam=new SpecParam();
            specParam.setGroupId(id);
            //通过规格组的id查询规格参数表
            List<SpecParam> specParamList=specParamMapper.select(specParam);
            //将表的规格参数封装到表中
            t.setSpecParamLists(specParamList);
        });
        return  specGroupList;
    }

    public List<SpecParam> querySpecParam(Long gid,Long cid, Boolean serching, Boolean gerenic) {
      SpecParam specParam=new SpecParam();
      specParam.setGroupId(gid);
      specParam.setCid(cid);
      specParam.setSerching(serching);
      specParam.setGeneric(gerenic);
      return  this.specParamMapper.select(specParam);

    }
}
