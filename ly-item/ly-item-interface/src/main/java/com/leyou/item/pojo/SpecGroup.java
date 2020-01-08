package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Data
@Table(name = "tb_spec_group")
public class SpecGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  Long cid;
    private String name;
    @Transient
    private List<SpecParam> specParamLists;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SpecParam> getSpecParamLists() {
        return specParamLists;
    }

    public void setSpecParamLists(List<SpecParam> specParamLists) {
        this.specParamLists = specParamLists;
    }
}
