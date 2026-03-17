package com.tableorder.table.application;

import com.tableorder.common.exception.BusinessException;
import com.tableorder.common.exception.NotFoundException;
import com.tableorder.store.adapter.out.persistence.StoreRepository;
import com.tableorder.store.domain.Store;
import com.tableorder.table.adapter.out.persistence.TableRepository;
import com.tableorder.table.adapter.out.persistence.TableSessionRepository;
import com.tableorder.table.domain.TableInfo;
import com.tableorder.table.domain.TableSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TableService {

    private final TableRepository tableRepository;
    private final TableSessionRepository tableSessionRepository;
    private final StoreRepository storeRepository;

    public TableService(TableRepository tableRepository, TableSessionRepository tableSessionRepository, StoreRepository storeRepository) {
        this.tableRepository = tableRepository;
        this.tableSessionRepository = tableSessionRepository;
        this.storeRepository = storeRepository;
    }

    @Transactional(readOnly = true)
    public List<TableInfo> findByStoreId(Long storeId) {
        return tableRepository.findByStoreId(storeId);
    }

    public TableInfo createTable(Long storeId, Integer tableNumber, String password) {
        if (tableRepository.findByStoreIdAndTableNumber(storeId, tableNumber).isPresent()) {
            throw new BusinessException("이미 존재하는 테이블 번호입니다: " + tableNumber);
        }
        return tableRepository.save(new TableInfo(storeId, tableNumber, password));
    }

    /**
     * 태블릿 인증: storeCode + tableNumber (비밀번호 검증 없음)
     */
    public TableAuthResult authenticate(String storeCode, Integer tableNumber, String password) {
        Store store = storeRepository.findByStoreCode(storeCode)
                .orElseThrow(() -> new BusinessException("매장을 찾을 수 없습니다"));
        TableInfo table = tableRepository.findByStoreIdAndTableNumber(store.getId(), tableNumber)
                .orElseThrow(() -> new BusinessException("테이블을 찾을 수 없습니다"));
        Optional<TableSession> activeSession = tableSessionRepository.findByTableIdAndActiveTrue(table.getId());
        String sessionToken = activeSession.map(TableSession::getSessionToken).orElse(null);
        return new TableAuthResult(store.getId(), table.getId(), sessionToken);
    }

    /**
     * 활성 세션 조회 또는 새 세션 생성 (첫 주문 시)
     */
    public TableSession getOrCreateSession(Long tableId) {
        return tableSessionRepository.findByTableIdAndActiveTrue(tableId)
                .orElseGet(() -> tableSessionRepository.save(new TableSession(tableId, UUID.randomUUID().toString())));
    }

    /**
     * 이용 완료
     */
    public void completeSession(Long tableId) {
        TableSession session = tableSessionRepository.findByTableIdAndActiveTrue(tableId)
                .orElseThrow(() -> new BusinessException("활성 세션이 없습니다"));
        session.complete();
        tableSessionRepository.save(session);
    }

    @Transactional(readOnly = true)
    public Optional<TableSession> findActiveSession(Long tableId) {
        return tableSessionRepository.findByTableIdAndActiveTrue(tableId);
    }

    public record TableAuthResult(Long storeId, Long tableId, String sessionToken) {}
}
