package com.tairitsu.driverincome.mapper;

import com.tairitsu.driverincome.dto.request.ExpenseDTORequest;
import com.tairitsu.driverincome.dto.response.ExpenseDTOResponse;
import com.tairitsu.driverincome.entity.Expense;

/**
 * Utility class for mapping between Expense entities and DTOs.
 */
public class ExpenseMapper {
    /**
     * Maps an expense request DTO to an Expense entity.
     * @param req expense request data
     * @return mapped expense entity
     */
    public static Expense mapToExpense(ExpenseDTORequest req){
        Expense expense = new Expense();
        expense.setAmount(req.getAmount());
        expense.setTypeOfExpense(req.getTypeOfExpense());
        expense.setExpenseDate(req.getExpenseDate());
        expense.setNote(req.getNote());
        return expense;
    }
    /**
     * Maps an Expense entity to a response DTO.
     * @param expense expense entity
     * @return mapped expense response
     */
    public static ExpenseDTOResponse mapToExpenseDTOResponse(Expense expense){
        return new ExpenseDTOResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getTypeOfExpense(),
                expense.getExpenseDate(),
                expense.getNote()
        );
    }
    /**
     * Updates mutable expense fields from request data.
     * <p>Blank notes are normalized to {@code null}.
     * @param req source request data
     * @param expense target expense entity to update
     */
    public static void updateExpenseFromRequest(ExpenseDTORequest req, Expense expense){
        expense.setAmount(req.getAmount());
        expense.setTypeOfExpense(req.getTypeOfExpense());
        expense.setExpenseDate(req.getExpenseDate());
        String note = req.getNote();
        String normalizedNote = (note != null && note.isBlank()) ? null : note;
        expense.setNote(normalizedNote);
    }
}
