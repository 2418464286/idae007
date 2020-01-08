package com.hp.item.controller;

import com.hp.item.service.SpecService;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecController {
    @Autowired
      private SpecService specService;
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroup(@PathVariable("cid") Long cid){
        List<SpecGroup> groupList=specService.querySpecGroup(cid);
            if (groupList!=null && groupList.size()>0){
                return ResponseEntity.ok(groupList);
            }
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> querySpecParam(@RequestParam(value = "gid",required =false)Long gid,
                                                          @RequestParam(value = "cid",required =false)Long cid,
                                                          @RequestParam(value = "serching",required =false)Boolean serching,
                                                          @RequestParam(value = "gerenic",required =false)Boolean gerenic){
        List<SpecParam> paramList=specService.querySpecParam(gid,cid,serching,gerenic);
        if (paramList!=null && paramList.size()>0){
            return ResponseEntity.ok(paramList);
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
