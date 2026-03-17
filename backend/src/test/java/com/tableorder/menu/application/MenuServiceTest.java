package com.tableorder.menu.application;

import com.tableorder.common.exception.NotFoundException;
import com.tableorder.menu.adapter.out.persistence.MenuRepository;
import com.tableorder.menu.adapter.out.persistence.OptionGroupRepository;
import com.tableorder.menu.adapter.out.persistence.OptionRepository;
import com.tableorder.menu.domain.Menu;
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
class MenuServiceTest {

    @Mock private MenuRepository menuRepository;
    @Mock private OptionGroupRepository optionGroupRepository;
    @Mock private OptionRepository optionRepository;

    @InjectMocks
    private MenuService menuService;

    @Test
    void findById_notFound_throwsException() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> menuService.findById(1L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void create_success() {
        when(menuRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        Menu result = menuService.create(1L, "아메리카노", 4500, "깊은 풍미", 1);
        assertThat(result.getName()).isEqualTo("아메리카노");
        assertThat(result.getPrice()).isEqualTo(4500);
    }

    @Test
    void delete_notFound_throwsException() {
        when(menuRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> menuService.delete(1L))
                .isInstanceOf(NotFoundException.class);
    }
}
