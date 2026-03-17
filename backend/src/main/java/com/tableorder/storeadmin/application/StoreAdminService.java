package com.tableorder.storeadmin.application;

import com.tableorder.common.exception.BusinessException;
import com.tableorder.common.exception.NotFoundException;
import com.tableorder.storeadmin.adapter.out.persistence.StoreAdminRepository;
import com.tableorder.storeadmin.domain.StoreAdmin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class StoreAdminService {

    private final StoreAdminRepository storeAdminRepository;

    public StoreAdminService(StoreAdminRepository storeAdminRepository) {
        this.storeAdminRepository = storeAdminRepository;
    }

    @Transactional(readOnly = true)
    public List<StoreAdmin> findAll() {
        return storeAdminRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<StoreAdmin> findByStoreId(Long storeId) {
        return storeAdminRepository.findByStoreId(storeId);
    }

    public StoreAdmin create(String username, Long storeId) {
        if (storeAdminRepository.existsByUsername(username)) {
            throw new BusinessException("이미 존재하는 사용자명입니다: " + username);
        }
        return storeAdminRepository.save(new StoreAdmin(username, storeId));
    }

    public void delete(Long id) {
        if (!storeAdminRepository.existsById(id)) {
            throw new NotFoundException("관리자를 찾을 수 없습니다: " + id);
        }
        storeAdminRepository.deleteById(id);
    }
}
