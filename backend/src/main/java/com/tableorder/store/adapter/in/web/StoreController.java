package com.tableorder.store.adapter.in.web;

import com.tableorder.store.application.StoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public List<StoreResponse> findAll() {
        return storeService.findAll().stream().map(StoreResponse::from).toList();
    }

    @GetMapping("/{id}")
    public StoreResponse findById(@PathVariable Long id) {
        return StoreResponse.from(storeService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoreResponse create(@Valid @RequestBody StoreRequest request) {
        return StoreResponse.from(storeService.create(request.name(), request.storeCode()));
    }

    @PutMapping("/{id}")
    public StoreResponse update(@PathVariable Long id, @Valid @RequestBody StoreRequest request) {
        return StoreResponse.from(storeService.update(id, request.name(), request.storeCode()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        storeService.delete(id);
    }
}
