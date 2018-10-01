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
        // null NRIC number
        Assert.assertThrows(NullPointerException.class, () -> Nric.isValid.test(null));

        // blank NRIC number
        assertFalse(Nric.isValid.test("")); // empty string
        assertFalse(Nric.isValid.test(" ")); // spaces only

        // missing parts
        assertFalse(Nric.isValid.test("2985637G")); // missing starting letter
        assertFalse(Nric.isValid.test("SG")); // missing digits
        assertFalse(Nric.isValid.test("S2985637")); // missing checksum letter

        // invalid parts
        assertFalse(Nric.isValid.test("A2985637G")); // invalid starting letter (must be S, T, F or G)
        assertFalse(Nric.isValid.test("s2985637G")); // lowercase starting letter
        assertFalse(Nric.isValid.test("SS2985637G")); // extra starting letter
        assertFalse(Nric.isValid.test("82985637G")); // starting character is a digit
        assertFalse(Nric.isValid.test("A123456G")); // insufficient digits
        assertFalse(Nric.isValid.test("A12345678G")); // excess digits
        assertFalse(Nric.isValid.test("A123456AG")); // letter in the digits (end)
        assertFalse(Nric.isValid.test("AA123456G")); // letter in the digits (beginning)
        assertFalse(Nric.isValid.test("A12A3456G")); // letter in the digits (middle)
        assertFalse(Nric.isValid.test("S2985637g")); // lowercase checksum letter
        assertFalse(Nric.isValid.test("S2985637GG")); // extra checksum letter
        assertFalse(Nric.isValid.test("S29856375")); // checksum character is a digit

        // spaces
        assertFalse(Nric.isValid.test(" S2985637G")); // leading space
        assertFalse(Nric.isValid.test("S 2985637G")); // space between starting letter and digits
        assertFalse(Nric.isValid.test("S298 5637G")); // space within digits
        assertFalse(Nric.isValid.test("S2985637 G")); // space between digits and checksum letter
        assertFalse(Nric.isValid.test("S2985637G ")); // trailing space

        // valid NRIC numbers
        assertTrue(Nric.isValid.test("S2985637G"));
        assertTrue(Nric.isValid.test("F0000000A"));
        assertTrue(Nric.isValid.test("G9999999Z"));
    }
}
