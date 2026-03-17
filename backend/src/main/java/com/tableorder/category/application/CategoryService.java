package com.tableorder.category.application;

import com.tableorder.category.adapter.out.persistence.CategoryRepository;
import com.tableorder.category.domain.Category;
import com.tableorder.common.exception.BusinessException;
import com.tableorder.common.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAllByOrderByDisplayOrderAsc();
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("카테고리를 찾을 수 없습니다: " + id));
    }

    public Category create(String name, Integer displayOrder) {
        if (categoryRepository.existsByName(name)) {
            throw new BusinessException("이미 존재하는 카테고리명입니다: " + name);
        }
        return categoryRepository.save(new Category(name, displayOrder));
    }

    public Category update(Long id, String name, Integer displayOrder) {
        Category category = findById(id);
        category.update(name, displayOrder);
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}
