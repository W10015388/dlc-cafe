package com.tableorder.menu.application;

import com.tableorder.common.exception.NotFoundException;
import com.tableorder.menu.adapter.out.persistence.MenuRepository;
import com.tableorder.menu.adapter.out.persistence.OptionGroupRepository;
import com.tableorder.menu.adapter.out.persistence.OptionRepository;
import com.tableorder.menu.domain.Menu;
import com.tableorder.menu.domain.Option;
import com.tableorder.menu.domain.OptionGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final OptionGroupRepository optionGroupRepository;
    private final OptionRepository optionRepository;

    public MenuService(MenuRepository menuRepository, OptionGroupRepository optionGroupRepository, OptionRepository optionRepository) {
        this.menuRepository = menuRepository;
        this.optionGroupRepository = optionGroupRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional(readOnly = true)
    public List<Menu> findByCategoryId(Long categoryId) {
        return menuRepository.findByCategoryIdOrderByDisplayOrderAsc(categoryId);
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Menu findById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("메뉴를 찾을 수 없습니다: " + id));
    }

    public Menu create(Long categoryId, String name, Integer price, String description, Integer displayOrder) {
        return menuRepository.save(new Menu(categoryId, name, price, description, displayOrder));
    }

    public Menu update(Long id, Long categoryId, String name, Integer price, String description, Integer displayOrder) {
        Menu menu = findById(id);
        menu.update(categoryId, name, price, description, displayOrder);
        return menuRepository.save(menu);
    }

    public void updateImage(Long id, String imageUrl) {
        Menu menu = findById(id);
        menu.setImageUrl(imageUrl);
        menuRepository.save(menu);
    }

    public void delete(Long id) {
        Menu menu = findById(id);
        menuRepository.delete(menu);
    }

    public boolean existsByCategoryId(Long categoryId) {
        return menuRepository.existsByCategoryId(categoryId);
    }

    // OptionGroup
    public OptionGroup createOptionGroup(Long menuId, String name, OptionGroup.SelectionType selectionType, Boolean required, Integer displayOrder) {
        findById(menuId); // validate menu exists
        return optionGroupRepository.save(new OptionGroup(menuId, name, selectionType, required, displayOrder));
    }

    public OptionGroup updateOptionGroup(Long groupId, String name, OptionGroup.SelectionType selectionType, Boolean required, Integer displayOrder) {
        OptionGroup group = optionGroupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("옵션 그룹을 찾을 수 없습니다: " + groupId));
        group.update(name, selectionType, required, displayOrder);
        return optionGroupRepository.save(group);
    }

    public void deleteOptionGroup(Long groupId) {
        optionGroupRepository.deleteById(groupId);
    }

    // Option
    public Option createOption(Long optionGroupId, String name, Integer additionalPrice, Integer displayOrder) {
        optionGroupRepository.findById(optionGroupId)
                .orElseThrow(() -> new NotFoundException("옵션 그룹을 찾을 수 없습니다: " + optionGroupId));
        return optionRepository.save(new Option(optionGroupId, name, additionalPrice, displayOrder));
    }

    public Option updateOption(Long optionId, String name, Integer additionalPrice, Integer displayOrder) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new NotFoundException("옵션을 찾을 수 없습니다: " + optionId));
        option.update(name, additionalPrice, displayOrder);
        return optionRepository.save(option);
    }

    public void deleteOption(Long optionId) {
        optionRepository.deleteById(optionId);
    }
}
