package com.hp.item.service;

import com.hp.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    public List<Category> queryByparentId(Long id){
        Category category=new Category();
        category.setParentId(id);
       return  categoryMapper.select(category);
    }

    public List<Category> queryByBrandId(Long id) {
        return  categoryMapper.queryByBrandId(id);
    }

    public List<String> queryNamesByIds(List<Long> asList) {
        // 74,75,76

        List<String> strings=new ArrayList<String>();
        //select * from tb_category where id in(74,75,76)
        List<Category> categoryList = this.categoryMapper.selectByIdList(asList);
        categoryList.forEach(t->{
            strings.add(t.getName());
        });
        return strings;
    }
    }
