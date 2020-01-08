package com.hp.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface BrandMapper extends Mapper<Brand> {
   @Insert("insert into tb_category_brand values(#{i},#{id})")
    void insertBrandCategory(@Param("i") Long i, @Param("id") Long id);
@Delete("delete from tb_category_brand where brand_id=#{id}")
    void deleteBrandCategory(@Param("id") Long id);
}
