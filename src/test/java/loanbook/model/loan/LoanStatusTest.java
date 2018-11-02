package loanbook.model.loan;

import static loanbook.model.loan.LoanStatus.isValidLoanStatus;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LoanStatusTest {
    @Test
    public void loanStatusToStringTest() {
        assertTrue(LoanStatus.ONGOING.toString().equals("Ongoing"));
        assertTrue(LoanStatus.RETURNED.toString().equals("Returned"));
        assertTrue(LoanStatus.DELETED.toString().equals("Deleted"));
    }

    @Test
    public void loanStatusNotValid() {
        assertTrue(isValidLoanStatus("ONGOING"));
        assertFalse(isValidLoanStatus("SSS"));
    }
}
