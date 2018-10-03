package seedu.address.model.loan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> Email.isValid.test(null));

        // blank email
        assertFalse(Email.isValid.test("")); // empty string
        assertFalse(Email.isValid.test(" ")); // spaces only

        // missing parts
        assertFalse(Email.isValid.test("@example.com")); // missing local part
        assertFalse(Email.isValid.test("peterjackexample.com")); // missing '@' symbol
        assertFalse(Email.isValid.test("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Email.isValid.test("peterjack@-")); // invalid domain name
        assertFalse(Email.isValid.test("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Email.isValid.test("peter jack@example.com")); // spaces in local part
        assertFalse(Email.isValid.test("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Email.isValid.test(" peterjack@example.com")); // leading space
        assertFalse(Email.isValid.test("peterjack@example.com ")); // trailing space
        assertFalse(Email.isValid.test("peterjack@@example.com")); // double '@' symbol
        assertFalse(Email.isValid.test("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Email.isValid.test("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Email.isValid.test("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Email.isValid.test("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Email.isValid.test("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Email.isValid.test("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(Email.isValid.test("PeterJack_1190@example.com"));
        assertTrue(Email.isValid.test("a@bc")); // minimal
        assertTrue(Email.isValid.test("test@localhost")); // alphabets only
        assertTrue(Email.isValid.test("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Email.isValid.test("123@145")); // numeric local part and domain name
        assertTrue(Email.isValid.test("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Email.isValid.test("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Email.isValid.test("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
