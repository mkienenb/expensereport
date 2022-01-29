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
}

public class ExpenseReport {
    public void printReport(List<Expense> expenses) {
        parameterizedPrintReport(expenses, new Date(), System.out);
    }

    public void parameterizedPrintReport(List<Expense> expenses, Date reportDate, PrintStream reportPrintStream) {
        int total = 0;
        int mealExpenses = 0;

        reportPrintStream.println("Expenses " + reportDate);

        for (Expense expense : expenses) {
            if (expense.type.isMealExpense()) {
                mealExpenses += expense.amount;
            }

            String expenseName = expense.type.reportName();

            String mealOverExpensesMarker = expense.type.isMealExpense() && expense.amount > expense.type.getOverExpenseAmount() ? "X" : " ";

            reportPrintStream.println(expenseName + "\t" + expense.amount + "\t" + mealOverExpensesMarker);

            total += expense.amount;
        }

        reportPrintStream.println("Meal expenses: " + mealExpenses);
        reportPrintStream.println("Total expenses: " + total);
    }
}
