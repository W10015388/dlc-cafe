package com.tableorder.store.application;

import com.tableorder.common.exception.BusinessException;
import com.tableorder.common.exception.NotFoundException;
import com.tableorder.store.adapter.out.persistence.StoreRepository;
import com.tableorder.store.domain.Store;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional(readOnly = true)
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Store findById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("매장을 찾을 수 없습니다: " + id));
    }

    @Transactional(readOnly = true)
    public Store findByStoreCode(String storeCode) {
        return storeRepository.findByStoreCode(storeCode)
                .orElseThrow(() -> new NotFoundException("매장을 찾을 수 없습니다: " + storeCode));
    }

    public Store create(String name, String storeCode) {
        if (storeRepository.existsByStoreCode(storeCode)) {
            throw new BusinessException("이미 존재하는 매장 코드입니다: " + storeCode);
        }
        return storeRepository.save(new Store(name, storeCode));
    }

    public Store update(Long id, String name, String storeCode) {
        Store store = findById(id);
        if (!store.getStoreCode().equals(storeCode) && storeRepository.existsByStoreCode(storeCode)) {
            throw new BusinessException("이미 존재하는 매장 코드입니다: " + storeCode);
        }
        store.update(name, storeCode);
        return storeRepository.save(store);
    }

    public void delete(Long id) {
        if (!storeRepository.existsById(id)) {
            throw new NotFoundException("매장을 찾을 수 없습니다: " + id);
        }
        storeRepository.deleteById(id);
    }
}
