package com.t248.lmf.crm.repository;

import com.t248.lmf.crm.entity.Dict;
import com.t248.lmf.crm.vo.DictInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DictRepostitor extends JpaRepository<Dict,Long> , JpaSpecificationExecutor<Dict>{

    public List<Dict> findDictsByDictType(String dictType);

    public Dict findDictsByDictValue(String value);
    @Query(nativeQuery = true,value = "SELECT d.`dict_value` id,d.`dict_item` name,COUNT(c.`cust_level`) count\n" +
            "FROM `bas_dict` d LEFT JOIN `cst_customer` c ON d.`dict_value`=c.`cust_level`\n" +
            "WHERE d.`dict_type`='客户等级'\n" +
            "GROUP BY d.`dict_value`")
    public List<DictInfo> getDictLeve();

    @Query(nativeQuery = true,value = "SELECT d.`dict_value` id,d.`dict_item` name,COUNT(s.`svr_type`) count\n" +
            "FROM `bas_dict` d LEFT JOIN `cst_service` s ON d.`dict_value`=s.`svr_type`\n" +
            "WHERE d.`dict_type`='服务类型'\n" +
            "GROUP BY s.`svr_type`")
    public List<DictInfo> getDicttype();

    @Query(value = "SELECT MAX(`dict_value`)\n" +
            "FROM `bas_dict`\n" +
            "WHERE `dict_type`='客户等级'",nativeQuery = true)
    public int getDictvalue();
}
