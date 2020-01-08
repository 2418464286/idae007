package com.hp.item.service;

import com.hp.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryMapper categoryMapper;
    public List<Category> queryByparentId(Long id){
        Category category=new Category();
        category.setParentId(id);
       return  categoryMapper.select(category);
    }

    public List<Category> queryByBrandId(Long id) {
        return  CategoryMapper.queryByBrandId(id);
    }

    public List<String> queryNamesByIds(List<Long> asList) {
    }
}
