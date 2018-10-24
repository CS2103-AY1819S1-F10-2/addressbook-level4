package seedu.address.model.loan;

import static org.junit.Assert.assertEquals;
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

    @Test
    public void censor() {
        Nric nric1 = new Nric("S1234567D");
        Nric nric2 = new Nric("T1234567J");
        Nric nric3 = new Nric("F1234567N");
        Nric nric4 = new Nric("G1234567X");
        Nric nric5 = new Nric("s1234567d");
        assertEquals("Sxxxxx67D", nric1.getCensored());
        assertEquals("Txxxxx67J", nric2.getCensored());
        assertEquals("Fxxxxx67N", nric3.getCensored());
        assertEquals("Gxxxxx67X", nric4.getCensored());
        assertEquals("Sxxxxx67D", nric5.getCensored());
    }

    @Test
    public void censorPartLengthCheck() {
        Nric testNric = new Nric("G1234567X");
        assertEquals("xxxxx", testNric.doCensoring(testNric.toString().length() - 4));
    }
}
