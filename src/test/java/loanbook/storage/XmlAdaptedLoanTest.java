package loanbook.storage;

import static loanbook.storage.XmlAdaptedLoan.MISSING_FIELD_MESSAGE_FORMAT;
import static loanbook.testutil.TypicalLoans.BENSON;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import loanbook.commons.exceptions.IllegalValueException;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Address;
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
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_BIKE = "001B^^E";
    private static final String INVALID_LOANRATE = "12.333";
    private static final String INVALID_LOANTIME = "2017-02-30 12:56";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_BIKE = BENSON.getBike().getName().toString();
    private static final String VALID_LOANRATE = BENSON.getLoanRate().toString();
    private static final String VALID_LOANTIMEA = BENSON.getLoanStartTime().toString();
    private static final String VALID_LOANTIMEB = BENSON.getLoanEndTime().toString();
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
                new XmlAdaptedLoan(INVALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeNullNameThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(null,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeInvalidNricThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_NAME,
                        INVALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = Nric.MESSAGE_NRIC_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeNullNricThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_NAME,
                        null,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeInvalidPhoneThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_NAME,
                        VALID_NRIC,
                        INVALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeNullPhoneThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_NAME,
                        VALID_NRIC,
                        null,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeInvalidEmailThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        INVALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeNullEmailThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        null,
                        VALID_ADDRESS,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeInvalidAddressThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        INVALID_ADDRESS,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeNullAddressThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        null,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeInvalidBikeThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        INVALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeNullBikeThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        null,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Bike.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeInvalidLoanRateThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_BIKE,
                        INVALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = LoanRate.MESSAGE_LOANRATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeNullLoanRateThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_BIKE,
                        null,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LoanRate.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeInvalidLoanTimeThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        INVALID_LOANTIME,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = LoanTime.MESSAGE_LOANTIME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeNullLoanTimeThrowsIllegalValueException() {
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        null,
                        VALID_LOANTIMEB,
                        VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LoanTime.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, loan::toModelType);
    }

    @Test
    public void toModelTypeInvalidTagsThrowsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedLoan loan =
                new XmlAdaptedLoan(VALID_NAME,
                        VALID_NRIC,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_BIKE,
                        VALID_LOANRATE,
                        VALID_LOANTIMEA,
                        VALID_LOANTIMEB,
                        invalidTags);
        Assert.assertThrows(IllegalValueException.class, loan::toModelType);
    }

}
