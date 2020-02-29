package com.t248.lmf.crm.service.impl;

import com.t248.lmf.crm.entity.Dict;
import com.t248.lmf.crm.entity.Product;
import com.t248.lmf.crm.entity.Storage;
import com.t248.lmf.crm.repository.DictRepostitor;
import com.t248.lmf.crm.repository.ProductRepostitor;
import com.t248.lmf.crm.repository.StorageRepostitor;
import com.t248.lmf.crm.service.DictService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class DictServiceImpl implements DictService {

    @Resource
    DictRepostitor dictRepostitor;

    @Override
    public Page<Dict> getDicts(String item, String type, Pageable pageable) {
        return dictRepostitor.findAll(new Specification<Dict>() {
            @Override
            public Predicate toPredicate(Root<Dict> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(item!=null&&item!=""){
                    predicates.add(cb.like(root.get("dictItem"),"%"+item+"%"));
                }
                if (type!=null&&type!=""){
                    predicates.add(cb.equal(root.get("dictType"),type));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        },pageable);
    }

    @Override
    public void save(Dict dict) {
        dictRepostitor.save(dict);
    }

    @Override
    public int getvaluemax() {
        return dictRepostitor.getDictvalue();
    }

    @Override
    public Dict getDict(Long dictId) {
        return dictRepostitor.getOne(dictId);
    }

    @Override
    public void deleteDict(Long dictId) {
        dictRepostitor.deleteById(dictId);
    }


    @Resource
    private ProductRepostitor productRepostitor;
    @Resource
    private StorageRepostitor storageRepostitor;

    @Override
    public Page<Product> getProducts(String prodName, String prodType, String prodBatch, Pageable pageable) {
        return productRepostitor.findAll(new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(prodName!=null&&prodName!=""){
                    predicates.add(cb.like(root.get("prodName"),"%"+prodName+"%"));
                }
                if(prodType!=null&&prodType!=""){
                    predicates.add(cb.like(root.get("prodType"),"%"+prodType+"%"));
                }
                if(prodBatch!=null&&prodBatch!=""){
                    predicates.add(cb.like(root.get("prodBatch"),"%"+prodBatch+"%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        },pageable);
    }

    @Override
    public Page<Storage> getStorage(String prodName, String stkWarehouse, Pageable pageable) {
        return storageRepostitor.findAll(new Specification<Storage>() {
            @Override
            public Predicate toPredicate(Root<Storage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(prodName!=null&&prodName!=""){
                    predicates.add(cb.like(root.get("product").get("prodName"),"%"+prodName+"%"));
                }
                if(stkWarehouse!=null&&stkWarehouse!=""){
                    predicates.add(cb.like(root.get("stkWarehouse"),"%"+stkWarehouse+"%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        },pageable);
    }
}
