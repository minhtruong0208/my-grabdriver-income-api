package com.tairitsu.driverincome.service.implement;

import com.tairitsu.driverincome.dto.request.ExpenseDTORequest;
import com.tairitsu.driverincome.dto.response.ExpenseDTOResponse;
import com.tairitsu.driverincome.entity.Expense;
import com.tairitsu.driverincome.exception.custom.ResourceNotFoundException;
import com.tairitsu.driverincome.repository.ExpenseRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplementTest {
    @Mock
    private ExpenseRepository expenseRepository;
    @InjectMocks
    private ExpenseServiceImplement expenseService;
    private Expense expense;
    private ExpenseDTORequest request;
    @BeforeEach
    void setUp() {
        expense = new Expense();
        expense.setId(1L);
        expense.setAmount(BigDecimal.valueOf(50));

        request = new ExpenseDTORequest();
        request.setAmount(BigDecimal.valueOf(50));
    }
    @Test
    void createExpense_HappyCase() {
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        ExpenseDTOResponse response = expenseService.createExpense(request);
        assertNotNull(response);
        verify(expenseRepository).save(any(Expense.class));
    }
    @Test
    void deleteExpense_HappyCase() {
        when(expenseRepository.existsById(1L)).thenReturn(true);
        expenseService.deleteExpense(1L);
        verify(expenseRepository).deleteById(1L);
    }
    @Test
    void deleteExpense_UnhappyCase_NotFound() {
        when(expenseRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class,
                () -> expenseService.deleteExpense(1L));
        verify(expenseRepository, never()).deleteById(anyLong());
    }
    @Test
    void getExpense_HappyCase() {
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        ExpenseDTOResponse response = expenseService.getExpense(1L);
        assertNotNull(response);
    }
    @Test
    void getExpense_UnhappyCase_NotFound() {
        when(expenseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> expenseService.getExpense(1L));
    }
    @Test
    void getAllExpense_HappyCase() {
        when(expenseRepository.findAll()).thenReturn(List.of(expense));
        List<ExpenseDTOResponse> responses = expenseService.getAllExpense();
        assertEquals(1, responses.size());
    }
    @Test
    void getAllExpense_EmptyList() {
        when(expenseRepository.findAll()).thenReturn(List.of());
        List<ExpenseDTOResponse> responses = expenseService.getAllExpense();
        assertTrue(responses.isEmpty());
    }
    @Test
    void updateExpense_HappyCase() {
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        ExpenseDTOResponse response = expenseService.updateExpense(1L, request);
        assertNotNull(response);
        verify(expenseRepository).save(any(Expense.class));
    }
    @Test
    void updateExpense_UnhappyCase_NotFound() {
        when(expenseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> expenseService.updateExpense(1L, request));
    }
    @Test
    void getExpenses_HappyCase() {
        Page<Expense> page = new PageImpl<>(List.of(expense));
        when(expenseRepository.findAll(PageRequest.of(0, 5)))
                .thenReturn(page);
        Page<ExpenseDTOResponse> responses = expenseService.getExpenses(0, 5);
        assertEquals(1, responses.getTotalElements());
    }
    @Test
    void getExpenses_EmptyPage() {
        Page<Expense> page = new PageImpl<>(List.of());
        when(expenseRepository.findAll(PageRequest.of(0, 5))).thenReturn(page);
        Page<ExpenseDTOResponse> responses = expenseService.getExpenses(0, 5);
        assertTrue(responses.isEmpty());
    }
}