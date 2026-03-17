package com.tableorder.storeadmin.application;

import com.tableorder.common.exception.BusinessException;
import com.tableorder.common.exception.NotFoundException;
import com.tableorder.storeadmin.adapter.out.persistence.StoreAdminRepository;
import com.tableorder.storeadmin.domain.StoreAdmin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreAdminServiceTest {

    @Mock
    private StoreAdminRepository storeAdminRepository;

    @InjectMocks
    private StoreAdminService storeAdminService;

    @Test
    void create_duplicateUsername_throwsException() {
        when(storeAdminRepository.existsByUsername("admin1")).thenReturn(true);
        assertThatThrownBy(() -> storeAdminService.create("admin1", 1L))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void create_success() {
        when(storeAdminRepository.existsByUsername("admin1")).thenReturn(false);
        when(storeAdminRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        StoreAdmin result = storeAdminService.create("admin1", 1L);
        assertThat(result.getUsername()).isEqualTo("admin1");
    }

    @Test
    void delete_notFound_throwsException() {
        when(storeAdminRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(() -> storeAdminService.delete(1L))
                .isInstanceOf(NotFoundException.class);
    }
}
