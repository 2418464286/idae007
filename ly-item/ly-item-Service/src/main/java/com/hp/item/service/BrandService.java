package com.hp.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hp.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.po.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;


    public PageResult<Brand> pageQuery(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //select count(*) from brand where name  like %key%  order by id desc

        //select * from brand where name  like %key%  order by id desc

        //开启分页
        PageHelper.startPage(page,rows);
        //查询条件
        Example example = new Example(Brand.class);

        if(StringUtils.isNotBlank(key)){
//            /获取criteria对象
            Example.Criteria criteria = example.createCriteria();
            criteria.andLike("name","%"+key+"%");
            //name  like %key%
        }
        //排序
        if(StringUtils.isNotBlank(sortBy)){
            //order by id desc
            //排序
            example.setOrderByClause(sortBy+ (desc?" desc":" asc"));
        }
        //查询
        Page<Brand> brandPage= (Page<Brand>)brandMapper.selectByExample(example);
        //select * from brand where name  like %key%  order by id desc
        //select count(*) from brand where name  like %key%  order by id desc


        //   private Long total;//一共多少条数据
        //    private Long tatalPage;//一共多少页
        //    private List<T> items;//每页显示的数据
        return new PageResult<>(brandPage.getTotal(),new Long(brandPage.getPages()),brandPage.getResult());

    }
@Transactional
    public void addBrand(Brand brand, List<Long> cids) {
        brandMapper.insertSelective(brand);
        for (Long i:cids){
              brandMapper.insertBrandCategory(i,brand.getId());
        }
    }

    public void updateBrand(Brand brand, List<Long> cids) {
        //更新表
        this.brandMapper.updateByPrimaryKey(brand);
        //删除中间表
        this.brandMapper.deleteBrandCategory(brand.getId());
        //关联中间表
        cids.forEach(t->{
                brandMapper.insertBrandCategory(t,brand.getId());
        });
    }
}
