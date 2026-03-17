package com.tableorder.menu.adapter.out.persistence;

import com.tableorder.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByCategoryIdOrderByDisplayOrderAsc(Long categoryId);
    boolean existsByCategoryId(Long categoryId);
}
