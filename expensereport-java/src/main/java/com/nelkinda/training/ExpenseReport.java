package com.nelkinda.training;

import java.io.PrintStream;
import java.util.Date;
import java.util.List;

enum ExpenseCategory {
    MEAL, OTHER
}
enum ExpenseType {
    DINNER ("Dinner", ExpenseCategory.MEAL, 5000),
    BREAKFAST ("Breakfast", ExpenseCategory.MEAL, 1000),
    CAR_RENTAL ("Car Rental", ExpenseCategory.OTHER, Integer.MAX_VALUE)
    ;

    private final ExpenseCategory expenseCategory;
    private final int overExpenseAmount;
    private String reportName;

    private ExpenseType(String name, ExpenseCategory expenseCategory, int overExpenseAmount) {
        this.expenseCategory = expenseCategory;
        this.overExpenseAmount = overExpenseAmount;
        this.reportName = name;
    }

    public boolean isMealExpense() {
        return ExpenseCategory.MEAL == expenseCategory;
    }

    public int getOverExpenseAmount() {
        return overExpenseAmount;
    }

    public String reportName() {
        return reportName;
    }

}

class Expense {
    ExpenseType type;
    int amount;

    public boolean isOverMealExpenseAmount() {
        return type.isMealExpense() && amount > type.getOverExpenseAmount();
    }
}

public class ExpenseReport {
    public void printReport(List<Expense> expenses) {
        parameterizedPrintReport(expenses, new Date(), System.out);
    }

    static class ExpensesCalculation {
        int total = 0;
        int mealExpenses = 0;
    }

    public void parameterizedPrintReport(List<Expense> expenses, Date reportDate, PrintStream reportPrintStream) {
        reportPrintStream.println("Expenses " + reportDate);

        ExpensesCalculation expensesCalculation = new ExpensesCalculation();
        for (Expense expense : expenses) {
            if (expense.type.isMealExpense()) {
                expensesCalculation.mealExpenses += expense.amount;
            }

            expensesCalculation.total += expense.amount;
        }

        for (Expense expense : expenses) {
            String expenseName = expense.type.reportName();

            String mealOverExpensesMarker = expense.isOverMealExpenseAmount() ? "X" : " ";

            reportPrintStream.println(expenseName + "\t" + expense.amount + "\t" + mealOverExpensesMarker);
        }

        reportPrintStream.println("Meal expenses: " + expensesCalculation.mealExpenses);
        reportPrintStream.println("Total expenses: " + expensesCalculation.total);
    }
}
