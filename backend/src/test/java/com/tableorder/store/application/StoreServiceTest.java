package com.tableorder.store.application;

import com.tableorder.common.exception.BusinessException;
import com.tableorder.common.exception.NotFoundException;
import com.tableorder.store.adapter.out.persistence.StoreRepository;
import com.tableorder.store.domain.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @Test
    void findAll_returnsAllStores() {
        when(storeRepository.findAll()).thenReturn(List.of(new Store("카페A", "CAFE01")));
        List<Store> result = storeService.findAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void findById_notFound_throwsException() {
        when(storeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> storeService.findById(1L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void create_duplicateCode_throwsException() {
        when(storeRepository.existsByStoreCode("CAFE01")).thenReturn(true);
        assertThatThrownBy(() -> storeService.create("카페A", "CAFE01"))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void create_success() {
        when(storeRepository.existsByStoreCode("CAFE01")).thenReturn(false);
        when(storeRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        Store result = storeService.create("카페A", "CAFE01");
        assertThat(result.getName()).isEqualTo("카페A");
    }

    @Test
    void delete_notFound_throwsException() {
        when(storeRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> storeService.delete(1L))
                .isInstanceOf(NotFoundException.class);
    }
}
