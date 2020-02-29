package com.t248.lmf.crm.service;

import com.t248.lmf.crm.entity.Dict;
import com.t248.lmf.crm.entity.Product;
import com.t248.lmf.crm.entity.Storage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DictService {
    public Page<Dict> getDicts(String item, String type, Pageable pageable);
    public void save(Dict dict);
    public int getvaluemax();
    public Dict getDict(Long dictId);
    public void deleteDict(Long dictId);

    public Page<Product> getProducts(String prodName, String prodType, String prodBatch, Pageable pageable);
    public Page<Storage> getStorage(String prodName, String stkWarehouse, Pageable pageable);
}
