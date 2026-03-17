package com.tableorder.category.adapter.out.persistence;

import com.tableorder.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByOrderByDisplayOrderAsc();
    boolean existsByName(String name);
}
