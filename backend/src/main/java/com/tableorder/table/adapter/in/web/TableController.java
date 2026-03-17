package com.tableorder.table.adapter.in.web;

import com.tableorder.table.application.TableService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping("/auth/table")
    public TableAuthResponse authenticate(@Valid @RequestBody TableAuthRequest request) {
        var result = tableService.authenticate(request.storeCode(), request.tableNumber(), request.password());
        return new TableAuthResponse(result.storeId(), result.tableId(), result.sessionToken());
    }

    @GetMapping("/tables")
    public List<TableResponse> findByStoreId(@RequestParam Long storeId) {
        return tableService.findByStoreId(storeId).stream().map(TableResponse::from).toList();
    }

    @PostMapping("/tables")
    @ResponseStatus(HttpStatus.CREATED)
    public TableResponse createTable(@Valid @RequestBody TableCreateRequest request) {
        return TableResponse.from(tableService.createTable(request.storeId(), request.tableNumber(), request.password()));
    }

    @PostMapping("/tables/{tableId}/complete")
    public void completeSession(@PathVariable Long tableId) {
        tableService.completeSession(tableId);
    }
}
