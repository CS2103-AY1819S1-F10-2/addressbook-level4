package seedu.address.model.loan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LoanFieldTest {

    @Test
    public void testEquals() {
        Nric nric = new Nric("S1234567A");
        Name name = new Name("S1234567A");
        assertFalse(nric.equals(name));
        assertFalse(name.equals(nric));
    }
}
