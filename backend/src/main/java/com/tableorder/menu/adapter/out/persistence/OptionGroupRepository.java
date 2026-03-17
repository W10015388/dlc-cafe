package com.tableorder.menu.adapter.out.persistence;

import com.tableorder.menu.domain.OptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OptionGroupRepository extends JpaRepository<OptionGroup, Long> {
    List<OptionGroup> findByMenuIdOrderByDisplayOrderAsc(Long menuId);
}
