package seedu.address.model.loan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Name.isValid.test(null));

        // invalid name
        assertFalse(Name.isValid.test("")); // empty string
        assertFalse(Name.isValid.test(" ")); // spaces only
        assertFalse(Name.isValid.test("^")); // only non-alphanumeric characters
        assertFalse(Name.isValid.test("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValid.test("peter jack")); // alphabets only
        assertTrue(Name.isValid.test("12345")); // numbers only
        assertTrue(Name.isValid.test("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValid.test("Capital Tan")); // with capital letters
        assertTrue(Name.isValid.test("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
