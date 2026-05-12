package com.tairitsu.driverincome.controller;

import com.tairitsu.driverincome.dto.request.ExpenseDTORequest;
import com.tairitsu.driverincome.dto.response.ExpenseDTOResponse;
import com.tairitsu.driverincome.service.ExpenseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    @PostMapping
    public ResponseEntity<ExpenseDTOResponse> addExpense(@Valid @RequestBody ExpenseDTORequest req) {
        return new ResponseEntity<>(expenseService.createExpense(req), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTOResponse> getExpense(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.getExpense(id));
    }
    @GetMapping("/all")
    public ResponseEntity<List<ExpenseDTOResponse>> getAllExpense() {
        return ResponseEntity.ok(expenseService.getAllExpense());
    }
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTOResponse> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseDTORequest req) {
        return ResponseEntity.ok(expenseService.updateExpense(id, req));
    }
    @GetMapping
    public ResponseEntity<Page<ExpenseDTOResponse>> getExpenses(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page index cannot be negative") int page,
            @RequestParam(defaultValue = "10") @Positive(message = "Page size must be greater than zero") int size) {
        return ResponseEntity.ok(expenseService.getExpenses(page, size));
    }
}
