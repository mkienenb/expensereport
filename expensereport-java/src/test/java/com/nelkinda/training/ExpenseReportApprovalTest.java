package com.nelkinda.training;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@UseReporter(DiffReporter.class)
@DisplayName("ExpenseReport Approval Tests")
public class ExpenseReportApprovalTest {

    private void testTemplatePrintReportForExpenses(List<Expense> expenses) {
        ByteArrayOutputStream fakeOutput = new ByteArrayOutputStream();
        PrintStream reportPrintStream = new PrintStream(fakeOutput);

        LocalDate reportLocalDate = LocalDate.of(2022, Month.JANUARY, 29);
        Date reportDate = Date.from(reportLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        ExpenseReport app = new ExpenseReport();
        app.parameterizedPrintReport(expenses, reportDate, reportPrintStream);

        String output = fakeOutput.toString();

        Approvals.verify(output);
    }

    private Expense createExpense(ExpenseType expenseType, int amount) {
        Expense expense = new Expense();
        expense.type = expenseType;
        expense.amount = amount;
        return expense;
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class A_report_has_zero_amounts {

        @Test
        void if_there_are_no_expense_items() {
            List<Expense> expenses = new ArrayList<>();
            testTemplatePrintReportForExpenses(expenses);
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class A_report_has_one_highlighted_expense_line {

        @Test
        void if_there_is_one_dinner_expense_item_for_8000() {
            Expense expense = createExpense(ExpenseType.DINNER, 8000);
            List<Expense> expenses = new ArrayList<>();
            expenses.add(expense);
            testTemplatePrintReportForExpenses(expenses);
        }

        @Test
        void if_there_is_one_breakfast_expense_item_for_1100() {
            Expense expense = createExpense(ExpenseType.BREAKFAST, 1100);
            List<Expense> expenses = new ArrayList<>();
            expenses.add(expense);
            testTemplatePrintReportForExpenses(expenses);
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class A_report_has_one_unhighlighted_expense_line {

        @Test
        void if_there_is_one_dinner_expense_item_for_2500() {
            Expense expense = createExpense(ExpenseType.DINNER, 2500);
            List<Expense> expenses = new ArrayList<>();
            expenses.add(expense);
            testTemplatePrintReportForExpenses(expenses);
        }

        @Test
        void if_there_is_one_breakfast_expense_item_for_900() {
            Expense expense = createExpense(ExpenseType.BREAKFAST, 900);
            List<Expense> expenses = new ArrayList<>();
            expenses.add(expense);
            testTemplatePrintReportForExpenses(expenses);
        }

        @Test
        void if_there_is_one_car_rental_expense_item_for_2500() {
            Expense dinnerExpense = createExpense(ExpenseType.CAR_RENTAL, 2500);
            List<Expense> expenses = new ArrayList<>();
            expenses.add(dinnerExpense);
            testTemplatePrintReportForExpenses(expenses);
        }
    }
}
