package com.tableorder.menu.adapter.in.web;

import com.tableorder.file.application.FileStorageService;
import com.tableorder.menu.application.MenuService;
import com.tableorder.menu.domain.OptionGroup;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
@RequestMapping("/api/v1/menus")
public class MenuController {

    private final MenuService menuService;
    private final FileStorageService fileStorageService;

    public MenuController(MenuService menuService, FileStorageService fileStorageService) {
        this.menuService = menuService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public List<MenuResponse> findAll(@RequestParam(required = false) Long categoryId) {
        if (categoryId != null) {
            return menuService.findByCategoryId(categoryId).stream().map(MenuResponse::from).toList();
        }
        return menuService.findAll().stream().map(MenuResponse::from).toList();
    }

    @GetMapping("/{id}")
    public MenuResponse findById(@PathVariable Long id) {
        return MenuResponse.from(menuService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuResponse create(@Valid @RequestBody MenuRequest request) {
        return MenuResponse.from(menuService.create(request.categoryId(), request.name(), request.price(),
                request.description(), request.displayOrder() != null ? request.displayOrder() : 0));
    }

    @PutMapping("/{id}")
    public MenuResponse update(@PathVariable Long id, @Valid @RequestBody MenuRequest request) {
        return MenuResponse.from(menuService.update(id, request.categoryId(), request.name(), request.price(),
                request.description(), request.displayOrder() != null ? request.displayOrder() : 0));
    }

    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<MenuResponse> uploadImage(@PathVariable Long id, @RequestPart("file") FilePart file) {
        return fileStorageService.store(file).map(imageUrl -> {
            menuService.updateImage(id, imageUrl);
            return MenuResponse.from(menuService.findById(id));
        });
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        menuService.delete(id);
    }

    // OptionGroup endpoints
    @PostMapping("/{menuId}/option-groups")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuResponse.OptionGroupResponse createOptionGroup(@PathVariable Long menuId, @Valid @RequestBody OptionGroupRequest request) {
        OptionGroup group = menuService.createOptionGroup(menuId, request.name(),
                OptionGroup.SelectionType.valueOf(request.selectionType()), request.required(),
                request.displayOrder() != null ? request.displayOrder() : 0);
        return MenuResponse.OptionGroupResponse.from(group);
    }

    @PutMapping("/option-groups/{groupId}")
    public MenuResponse.OptionGroupResponse updateOptionGroup(@PathVariable Long groupId, @Valid @RequestBody OptionGroupRequest request) {
        OptionGroup group = menuService.updateOptionGroup(groupId, request.name(),
                OptionGroup.SelectionType.valueOf(request.selectionType()), request.required(),
                request.displayOrder() != null ? request.displayOrder() : 0);
        return MenuResponse.OptionGroupResponse.from(group);
    }

    @DeleteMapping("/option-groups/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOptionGroup(@PathVariable Long groupId) {
        menuService.deleteOptionGroup(groupId);
    }

    // Option endpoints
    @PostMapping("/option-groups/{groupId}/options")
    @ResponseStatus(HttpStatus.CREATED)
    public MenuResponse.OptionResponse createOption(@PathVariable Long groupId, @Valid @RequestBody OptionRequest request) {
        return MenuResponse.OptionResponse.from(menuService.createOption(groupId, request.name(),
                request.additionalPrice(), request.displayOrder() != null ? request.displayOrder() : 0));
    }

    @PutMapping("/options/{optionId}")
    public MenuResponse.OptionResponse updateOption(@PathVariable Long optionId, @Valid @RequestBody OptionRequest request) {
        return MenuResponse.OptionResponse.from(menuService.updateOption(optionId, request.name(),
                request.additionalPrice(), request.displayOrder() != null ? request.displayOrder() : 0));
    }

    @DeleteMapping("/options/{optionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOption(@PathVariable Long optionId) {
        menuService.deleteOption(optionId);
    }
}
