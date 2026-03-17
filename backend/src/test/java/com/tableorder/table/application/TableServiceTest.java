package com.tableorder.table.application;

import com.tableorder.common.exception.BusinessException;
import com.tableorder.store.adapter.out.persistence.StoreRepository;
import com.tableorder.store.domain.Store;
import com.tableorder.table.adapter.out.persistence.TableRepository;
import com.tableorder.table.adapter.out.persistence.TableSessionRepository;
import com.tableorder.table.domain.TableInfo;
import com.tableorder.table.domain.TableSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TableServiceTest {

    @Mock private TableRepository tableRepository;
    @Mock private TableSessionRepository tableSessionRepository;
    @Mock private StoreRepository storeRepository;

    @InjectMocks
    private TableService tableService;

    @Test
    void authenticate_wrongPassword_throwsException() {
        Store store = new Store("카페A", "CAFE01");
        when(storeRepository.findByStoreCode("CAFE01")).thenReturn(Optional.of(store));
        TableInfo table = new TableInfo(1L, 1, "1234");
        when(tableRepository.findByStoreIdAndTableNumber(any(), eq(1))).thenReturn(Optional.of(table));

        assertThatThrownBy(() -> tableService.authenticate("CAFE01", 1, "wrong"))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void getOrCreateSession_noActiveSession_createsNew() {
        when(tableSessionRepository.findByTableIdAndActiveTrue(1L)).thenReturn(Optional.empty());
        when(tableSessionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        TableSession session = tableService.getOrCreateSession(1L);
        assertThat(session.getActive()).isTrue();
        assertThat(session.getSessionToken()).isNotNull();
    }

    @Test
    void completeSession_noActiveSession_throwsException() {
        when(tableSessionRepository.findByTableIdAndActiveTrue(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> tableService.completeSession(1L))
                .isInstanceOf(BusinessException.class);
    }
}
