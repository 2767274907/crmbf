package com.t248.lmf.crm.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 字典表
 */
@Entity
@Table(name = "bas_dict")
public class Dict implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dict_id")
    private Long dictId;
    @Column(name = "dict_type")
    private String dictType;
    @Column(name = "dict_item")
    private String dictItem;
    @Column(name = "dict_value")
    private String dictValue;
    @Column(name = "dict_is_editable")
    private Long dictIsEditable;

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictItem() {
        return dictItem;
    }

    public void setDictItem(String dictItem) {
        this.dictItem = dictItem;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public Long getDictIsEditable() {
        return dictIsEditable;
    }

    public void setDictIsEditable(Long dictIsEditable) {
        this.dictIsEditable = dictIsEditable;
    }
}
