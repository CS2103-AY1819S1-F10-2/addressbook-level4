package loanbook.model.loan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import loanbook.testutil.Assert;

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
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName(".")); // only supported non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains unsupported non-alphanumeric characters
        assertFalse(Name.isValidName("-peter")); // starts with a supported non-alphanumeric character

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Name.isValidName("Tan Wei Liang, Joseph")); // has a comma
        assertTrue(Name.isValidName("Jeremy O'Donagall")); // has an apostrophe
        assertTrue(Name.isValidName("Abu Karim Muhammad al-Jamil")); // has a hyphen
        assertTrue(Name.isValidName("Daisy M. Penrose")); // has a period
    }
}
