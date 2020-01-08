package com.hp.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hp.item.mapper.*;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.*;
import com.leyou.po.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class GoodsService {
    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private CategoryService categorySerivce;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

    public PageResult<SpuBo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {
        //开启分页
        PageHelper.startPage(page,rows);
        //select * from tb_spu where title like "%"+小米+“%” and saleable=true
        //查询对象
        Example example = new Example(Spu.class);
       //获取criteria
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
            //title like "%"+小米+“%”
        }
        if(null!=saleable){
            criteria.andEqualTo("saleable",saleable);
            //and saleable=true
        }
        //查询结果
        Page<Spu> spuPage=(Page<Spu>) this.spuMapper.selectByExample(example);

        List<Spu> result = spuPage.getResult();

        List<SpuBo> spuBos=new ArrayList<SpuBo>();

        for(Spu spu:result){
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu,spuBo);
            //通过分类id得到 分类名称
            List<String> names=this.categorySerivce.queryNamesByIds(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()));
            //拼接
            String join = StringUtils.join(names, "/");
            //分类名称
            spuBo.setCname(join);
            //根据品牌id查询品牌
            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuBo.setBname(brand.getName());
            spuBos.add(spuBo);
        }
        return new PageResult<>(spuPage.getTotal(),new Long(spuPage.getPages()),spuBos);
    }
    @Transactional
    public void saveGoods(SpuBo spuBo) {
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreatetime(new Date());
        spuBo.setLastupdatetime(new Date());
        //保存到spu数据
        this.spuMapper.insertSelective(spuBo);

        Long id = spuBo.getId();

        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(id);
        //保存到spudetail数据
        this.spuDetailMapper.insert(spuDetail);

        List<Sku> skus = spuBo.getSkus();
        //保存sku和stock
        saveSkus(spuBo,skus);


    }

    private void saveSkus(SpuBo spuBo, List<Sku> skus) {
        for(Sku s:skus){
            s.setSpuId(spuBo.getId());
            s.setCreateTime(new Date());
            s.setLastUpdateTime(new Date());
            //保存
            this.skuMapper.insertSelective(s);

            Stock stock = new Stock();
            stock.setSkuId(s.getId());
            stock.setStock(s.getStock());
            this.stockMapper.insert(stock);


        }

    }

    public SpuDetail querySpuDetailBySpuId(Long id) {

        return this.spuDetailMapper.selectByPrimaryKey(id);
    }

    public List<Sku> querySkuBySpuId(Long id) {
        Sku sku = new Sku();
        sku.setSpuId(id);
        //select * from tb_sku where spu_id=196
        List<Sku> skuList = skuMapper.select(sku);

        for(Sku s:skuList){
            Long id1 = s.getId();
            Stock stock = this.stockMapper.selectByPrimaryKey(id1);
            s.setStock(stock.getStock());

        }
        return skuList;

    }

    @Transactional
    public void updateGoods(SpuBo spuBo) {
        spuBo.setLastupdatetime(new Date());
        //更新spu
        this.spuMapper.updateByPrimaryKey(spuBo);
        //更新spudetail
        this.spuDetailMapper.updateByPrimaryKey(spuBo.getSpuDetail());

       //先查询sku
        Long id = spuBo.getId();
        Sku sku = new Sku();
        sku.setSpuId(id);

        List<Sku> skuList = this.skuMapper.select(sku);
        //select * from tb_sku where spu_id=196

        for(Sku s:skuList){
            //删除sku stock
            this.stockMapper.deleteByPrimaryKey(s.getId());
            this.skuMapper.delete(s);
        }
        //新增
        saveSkus(spuBo,spuBo.getSkus());
    }
}
