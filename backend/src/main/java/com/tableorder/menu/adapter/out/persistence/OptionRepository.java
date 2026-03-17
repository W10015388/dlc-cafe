package com.tableorder.menu.adapter.out.persistence;

import com.tableorder.menu.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
