package com.hp.page.client;


import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("item-service")
public interface SpecClient  {


    @GetMapping("spec/groups/{cid}")
    public List<SpecGroup> querySpecGroups(@PathVariable("cid") Long id);
    @GetMapping("spec/params")
    public  List<SpecParam> querySpecParam(@RequestParam(value = "gid", required = false) Long gid,
                                           @RequestParam(value = "cid", required = false) Long cid,
                                           @RequestParam(value = "searching", required = false) Boolean searching,
                                           @RequestParam(value = "generic", required = false) Boolean generic);

}
