package loanbook.model.loan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import loanbook.model.loan.exceptions.SameLoanStatusException;
import loanbook.testutil.Assert;
import loanbook.testutil.LoanBuilder;

public class LoanStatusTest {
    @Test
    public void loanStatusToStringTest() {
        assertTrue(LoanStatus.ONGOING.toString().equals("Ongoing"));
        assertTrue(LoanStatus.RETURNED.toString().equals("Returned"));
        assertTrue(LoanStatus.DELETED.toString().equals("Deleted"));
    }
}
