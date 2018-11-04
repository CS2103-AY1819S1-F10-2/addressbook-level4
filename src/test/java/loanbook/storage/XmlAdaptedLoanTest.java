package loanbook.storage;

import static loanbook.storage.XmlAdaptedLoan.MISSING_FIELD_MESSAGE_FORMAT;
import static loanbook.testutil.TypicalLoans.BENSON;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import loanbook.commons.exceptions.IllegalValueException;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Email;
import loanbook.model.loan.LoanRate;
import loanbook.model.loan.LoanTime;
import loanbook.model.loan.Name;
import loanbook.model.loan.Nric;
import loanbook.model.loan.Phone;
import loanbook.testutil.Assert;

public class XmlAdaptedLoanTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_NRIC = "A12#4567B";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_BIKE = "001B^^E";
    private static final String INVALID_LOANRATE = "12.333";
    private static final String INVALID_LOANTIME = "2017-02-30 12:56";
    private static final String INVALID_TAG = "#friend";

    private static final XmlAdaptedLoanId VALID_LOANID = new XmlAdaptedLoanId(BENSON.getLoanId());
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_BIKE = BENSON.getBike().getName().toString();
    private static final String VALID_LOANRATE = BENSON.getLoanRate().toString();
    private static final String VALID_LOANTIMEA = BENSON.getLoanStartTime().toString();
    private static final String VALID_LOANTIMEB = BENSON.getLoanEndTime().toString();
    private static final String VALID_LOANSTATUS = BENSON.getLoanStatus().name();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelTypeValidLoanDetails_returnsLoan() throws Exception {
        XmlAdaptedLoan loan = new XmlAdaptedLoan(BENSON);
        assertEquals(BENSON, loan.toModelType());
    }

    @Test
    public void toModelTypeInvalidNameThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_LOANID,
                        INVALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeNullNameThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_LOANID,
                        null,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeInvalidNricThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_LOANID,
                        VALID_NAME,
                        INVALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);
        String expectedMessage = Nric.MESSAGE_NRIC_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeNullNricThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_LOANID,
                        VALID_NAME,
                        null,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeInvalidPhoneThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_LOANID,
                        VALID_NAME,
                        VALID_NRIC,
                        INVALID_PHONE,
                        VALID_EMAIL,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeNullPhoneThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_LOANID,
                        VALID_NAME,
                        VALID_NRIC,
                        null,
                        VALID_EMAIL,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeInvalidEmailThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_LOANID,
                        VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        INVALID_EMAIL,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeNullEmailThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_LOANID,
                        VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        null,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeInvalidBikeThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_LOANID,
                        VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        INVALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeNullBikeThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_LOANID,
                        VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        null,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Bike.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeInvalidLoanRateThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_LOANID,
                        VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_BIKE,
                        INVALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);
        String expectedMessage = LoanRate.MESSAGE_LOANRATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeNullLoanRateThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_LOANID,
                        VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_BIKE,
                        null,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LoanRate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeInvalidLoanTimeThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_LOANID,
                        VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        INVALID_LOANTIME,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);
        String expectedMessage = LoanTime.MESSAGE_LOANTIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeNullLoanTimeThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_LOANID,
                        VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        null,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LoanTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeInvalidTagsThrowsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_LOANID,
                        VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        invalidTags);
        Assert.assertThrows(IllegalValueException.class, loan::toModelType);
    }

    @Test
    public void equalityTest() {
        XmlAdaptedLoan xmlAdaptedLoan1 =
                new XmlAdaptedLoan(VALID_LOANID,
                        VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);

        XmlAdaptedLoan xmlAdaptedLoan2 =
                new XmlAdaptedLoan(BENSON);

        XmlAdaptedLoan xmlAdaptedLoan3 =
                new XmlAdaptedLoan(VALID_LOANID,
                        "DefinitelyNotTheSameName",
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_LOANSTATUS,
                        VALID_TAGS);

        assertEquals(xmlAdaptedLoan1, xmlAdaptedLoan1); // Same instance
        assertEquals(xmlAdaptedLoan1, xmlAdaptedLoan2); // Same value
        assertNotEquals(xmlAdaptedLoan1, xmlAdaptedLoan3); // Different value
        assertNotEquals(xmlAdaptedLoan1, "Different type");
    }

    @Test
    public void equalityTestXmlAdatedTag() {
        XmlAdaptedTag xmlTag1 = new XmlAdaptedTag("tag");
        XmlAdaptedTag xmlTag2 = new XmlAdaptedTag("tag");
        XmlAdaptedTag xmlTag3 = new XmlAdaptedTag("notATag");

        assertEquals(xmlTag1, xmlTag1); // Same instance
        assertEquals(xmlTag1, xmlTag2); // Same value
        assertNotEquals(xmlTag1, xmlTag3); // Different value
        assertNotEquals(xmlTag1, "Different type");
    }
}
