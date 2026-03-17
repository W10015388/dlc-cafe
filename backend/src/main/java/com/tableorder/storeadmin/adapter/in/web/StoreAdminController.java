package com.tableorder.storeadmin.adapter.in.web;

import com.tableorder.storeadmin.application.StoreAdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/store-admins")
public class StoreAdminController {

    private final StoreAdminService storeAdminService;

    public StoreAdminController(StoreAdminService storeAdminService) {
        this.storeAdminService = storeAdminService;
    }

    @GetMapping
    public List<StoreAdminResponse> findAll(@RequestParam(required = false) Long storeId) {
        if (storeId != null) {
            return storeAdminService.findByStoreId(storeId).stream().map(StoreAdminResponse::from).toList();
        }
        return storeAdminService.findAll().stream().map(StoreAdminResponse::from).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoreAdminResponse create(@Valid @RequestBody StoreAdminRequest request) {
        return StoreAdminResponse.from(storeAdminService.create(request.username(), request.storeId()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        storeAdminService.delete(id);
    }
}
