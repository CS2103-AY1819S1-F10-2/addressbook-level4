package seedu.address.model.loan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class NricTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructor_invalidNric_throwsIllegalArgumentException() {
        String invalidNric = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void isValidNric() {
        // null nric
        Assert.assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        // invalid nric
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only
        assertFalse(Nric.isValidNric("^")); // only non-alphanumeric characters
        assertFalse(Nric.isValidNric("njkakjsdnfa")); // contains random alphanumeric characters

        // Wrong checksum
        assertFalse(Nric.isValidNric("S1234567Z"));
        assertFalse(Nric.isValidNric("T1234567Z"));
        assertFalse(Nric.isValidNric("F1234567Z"));
        assertFalse(Nric.isValidNric("G1234567Z"));

        // valid nric
        assertTrue(Nric.isValidNric("S1234567D")); // IC for citizens born in 20th century
        assertTrue(Nric.isValidNric("T1234567J")); // IC for citizens born in 21th century
        assertTrue(Nric.isValidNric("F1234567N")); // Foreign IC F
        assertTrue(Nric.isValidNric("G1234567X")); // Foreign IC G

        // Inconsistant case
        assertTrue(Nric.isValidNric("s1234567d"));
        assertTrue(Nric.isValidNric("t1234567j"));
        assertTrue(Nric.isValidNric("f1234567N"));
        assertTrue(Nric.isValidNric("G1234567x"));
    }
}
