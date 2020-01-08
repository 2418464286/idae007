package com.hp.item.mapper;


import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
public interface CategoryMapper extends Mapper<Category> {
    @Select("select c.*from tb_category c,tb_category_brand cb where c.id=cb.category and brand_id=#{id}")
     List<Category> queryByBrandId(@Param("id") Long id);
}