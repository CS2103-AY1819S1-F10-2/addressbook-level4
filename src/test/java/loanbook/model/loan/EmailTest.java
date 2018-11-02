package loanbook.model.loan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import loanbook.testutil.Assert;

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
    public void isValidGmail() {
        final String invalidGmail1 = "123456";
        final String invalidGmail2 = "123456@";
        final String invalidGmail3 = "123456@gmail";
        final String invalidGmail4 = "123456@outlook.com";
        final String invalidGmail5 = "123456@gmail.com@gmail.com";
        final String invalidGmail6 = "dotbeforeat.@gmail.com";
        final String invalidGmail7 = "two..dots@gmail.com";
        final String invalidGmail8 = "$ymbols@gmail.com";
        final String invalidGmail9 = "symbolB*tween@gmail.com";
        final String invalidGmail10 = "some spaces@gmail.com";
        final String validGmail1 = "example@gmail.com";
        final String validGmail2 = "aaaaa.aaaa@gmail.com";
        final String validGmail3 = "bbb.aaa.090@gmail.com";

        //invalid domain -> return false
        assertFalse(Email.isValidGmail(invalidGmail1));
        assertFalse(Email.isValidGmail(invalidGmail2));
        assertFalse(Email.isValidGmail(invalidGmail3));
        assertFalse(Email.isValidGmail(invalidGmail5));

        //not gmail -> return false
        assertFalse(Email.isValidGmail(invalidGmail4));

        //invalid local -> return false
        assertFalse(Email.isValidGmail(invalidGmail6));
        assertFalse(Email.isValidGmail(invalidGmail7));
        assertFalse(Email.isValidGmail(invalidGmail8));
        assertFalse(Email.isValidGmail(invalidGmail9));
        assertFalse(Email.isValidGmail(invalidGmail10));


        //valid gmail -> returns true
        assertTrue(Email.isValidGmail(validGmail1));
        assertTrue(Email.isValidGmail(validGmail2));
        assertTrue(Email.isValidGmail(validGmail3));
    }

    @Test
    public void isValidEmail() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // blank email
        assertFalse(Email.isValidEmail("")); // empty string
        assertFalse(Email.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Email.isValidEmail("@example.com")); // missing local part
        assertFalse(Email.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Email.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Email.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Email.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Email.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Email.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Email.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(Email.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(Email.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Email.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Email.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Email.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Email.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Email.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Email.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(Email.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(Email.isValidEmail("a@bc")); // minimal
        assertTrue(Email.isValidEmail("test@localhost")); // alphabets only
        assertTrue(Email.isValidEmail("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Email.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(Email.isValidEmail("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Email.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }

    @Test
    public void censor() {
        Email email1 = new Email("a@abc.com");
        Email email2 = new Email("ab@abc.com");
        Email email3 = new Email("abc@abc.com");
        Email email4 = new Email("abcd@abc.com");
        Email email5 = new Email("abcde@abc.com");
        Email email6 = new Email("loooooooooooooooong@abc.com");
        assertEquals("a@abc.com", email1.getCensored());
        assertEquals("ab@abc.com", email2.getCensored());
        assertEquals("abc@abc.com", email3.getCensored());
        assertEquals("axcd@abc.com", email4.getCensored());
        assertEquals("axxde@abc.com", email5.getCensored());
        assertEquals("lxxxxxxxxxxxxxxxxng@abc.com", email6.getCensored());
    }

    @Test
    public void censorPartLengthCheck() {
        Email testEmail = new Email("abc@abc.com");
        assertEquals("", testEmail.doCensoring(1));
        assertEquals("", testEmail.doCensoring(2));
        assertEquals("", testEmail.doCensoring(3));
        assertEquals("x", testEmail.doCensoring(4));
        assertEquals("xx", testEmail.doCensoring(5));
        assertEquals("xxxxxxxxxx", testEmail.doCensoring(13));
    }
}
