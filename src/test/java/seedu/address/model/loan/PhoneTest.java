package seedu.address.model.loan;

import static org.junit.Assert.assertEquals;
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
        Assert.assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
    }

    @Test
    public void censor() {
        Phone phone1 = new Phone("911");
        Phone phone2 = new Phone("1234");
        Phone phone3 = new Phone("12345");
        Phone phone4 = new Phone("123456");
        Phone phone5 = new Phone("1234567");
        Phone phone6 = new Phone("12345678");
        Phone phone7 = new Phone("124293842033123");
        assertEquals("911", phone1.getCensored());
        assertEquals("1x34", phone2.getCensored());
        assertEquals("1xx45", phone3.getCensored());
        assertEquals("1xxx56", phone4.getCensored());
        assertEquals("1xxxx67", phone5.getCensored());
        assertEquals("1xxxxx78", phone6.getCensored());
        assertEquals("1xxxxxxxxxxxx23", phone7.getCensored());
    }

    @Test
    public void censorPartLengthCheck() {
        Phone testPhone = new Phone("90000000");
        assertEquals("", testPhone.doCensoring(3));
        assertEquals("x", testPhone.doCensoring(4));
        assertEquals("xx", testPhone.doCensoring(5));
        assertEquals("xxx", testPhone.doCensoring(6));
    }
}
