package seedu.address.model.loan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Phone.isValid.test(null));

        // invalid phone numbers
        assertFalse(Phone.isValid.test("")); // empty string
        assertFalse(Phone.isValid.test(" ")); // spaces only
        assertFalse(Phone.isValid.test("91")); // less than 3 numbers
        assertFalse(Phone.isValid.test("phone")); // non-numeric
        assertFalse(Phone.isValid.test("9011p041")); // alphabets within digits
        assertFalse(Phone.isValid.test("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Phone.isValid.test("911")); // exactly 3 numbers
        assertTrue(Phone.isValid.test("93121534"));
        assertTrue(Phone.isValid.test("124293842033123")); // long phone numbers
    }
}
