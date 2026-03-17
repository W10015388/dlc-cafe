package com.tableorder.category.application;

import com.tableorder.category.adapter.out.persistence.CategoryRepository;
import com.tableorder.category.domain.Category;
import com.tableorder.common.exception.BusinessException;
import com.tableorder.common.exception.NotFoundException;
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
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void create_duplicateName_throwsException() {
        when(categoryRepository.existsByName("커피(HOT)")).thenReturn(true);
        assertThatThrownBy(() -> categoryService.create("커피(HOT)", 0))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void create_success() {
        when(categoryRepository.existsByName("커피(HOT)")).thenReturn(false);
        when(categoryRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        Category result = categoryService.create("커피(HOT)", 1);
        assertThat(result.getName()).isEqualTo("커피(HOT)");
    }

    @Test
    void findById_notFound_throwsException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> categoryService.findById(1L))
                .isInstanceOf(NotFoundException.class);
    }
}
