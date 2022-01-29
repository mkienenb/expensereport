package com.nelkinda.training;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.DiffReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@UseReporter(DiffReporter.class)
@DisplayName("ExpenseReport Approval Tests")
public class ExpenseReportApprovalTest {
    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class A_report_has_zero_amounts {

        @Test
        void if_there_are_no_expense_items() {
            ByteArrayOutputStream fakeOutput = new ByteArrayOutputStream();
            System.setOut(new PrintStream(fakeOutput));

            List<Expense> expenses = new ArrayList<>();
            ExpenseReport app = new ExpenseReport();
            app.printReport(expenses);

            String output = fakeOutput.toString();

            Approvals.verify(output);
        }
    }
}
