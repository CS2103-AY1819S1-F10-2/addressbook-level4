package seedu.address.model.loan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.loan.exceptions.SameLoanStatusException;
import seedu.address.testutil.Assert;
import seedu.address.testutil.LoanBuilder;

public class LoanStatusTest {
    @Test
    public void changeLoanStatus_sameLoanStatus_throwsDuplicateLoanException() {
        Loan loan = new LoanBuilder().build();
        Assert.assertThrows(SameLoanStatusException.class, () -> loan.changeLoanStatus(LoanStatus.valueOf("ONGOING")));

        loan.changeLoanStatus(LoanStatus.valueOf("RETURNED"));
        Assert.assertThrows(SameLoanStatusException.class, () -> loan.changeLoanStatus(LoanStatus.valueOf("RETURNED")));

        loan.changeLoanStatus(LoanStatus.valueOf("DELETED"));
        Assert.assertThrows(SameLoanStatusException.class, () -> loan.changeLoanStatus(LoanStatus.valueOf("DELETED")));
    }

    @Test
    public void changeLoanStatus_differentLoanStatus_statusChangedSuccessfully() {
        Loan loan = new LoanBuilder().build();
        assertTrue(loan.getLoanStatus().equals(LoanStatus.ONGOING));
        assertFalse(loan.getLoanStatus().equals(LoanStatus.RETURNED));

        loan.changeLoanStatus(LoanStatus.valueOf("RETURNED"));
        assertTrue(loan.getLoanStatus().equals(LoanStatus.RETURNED));
        assertFalse(loan.getLoanStatus().equals(LoanStatus.DELETED));

        loan.changeLoanStatus(LoanStatus.valueOf("DELETED"));
        assertTrue(loan.getLoanStatus().equals(LoanStatus.DELETED));
        assertFalse(loan.getLoanStatus().equals(LoanStatus.RETURNED));
    }

    @Test
    public void loanStatusToStringTest() {
        assertTrue(LoanStatus.ONGOING.toString().equals("Ongoing"));
        assertTrue(LoanStatus.RETURNED.toString().equals("Returned"));
        assertTrue(LoanStatus.DELETED.toString().equals("Deleted"));
    }
}
