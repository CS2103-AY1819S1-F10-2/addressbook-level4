package loanbook.storage;

import static loanbook.testutil.Assert.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import loanbook.commons.exceptions.IllegalValueException;
import loanbook.model.loan.LoanId;

public class XmlAdaptedLoanIdTest {

    @Test
    public void toModelTypeValidId() throws IllegalValueException {
        LoanId validLoanId = new LoanId("500");
        XmlAdaptedLoanId xmlAdaptedLoanId1 = new XmlAdaptedLoanId(validLoanId); // Construct from LoanId
        XmlAdaptedLoanId xmlAdaptedLoanId2 = new XmlAdaptedLoanId(500); // Construct from integer

        assertEquals(validLoanId, xmlAdaptedLoanId1.toModelType());
        assertEquals(validLoanId, xmlAdaptedLoanId2.toModelType());
    }

    @Test
    public void toModelTypeInvalidIdThrowsIllegalValueException() {
        XmlAdaptedLoanId xmlAdaptedLoanId = new XmlAdaptedLoanId(1234567890); // More than 9 digits
        String expectedMessage = LoanId.MESSAGE_LOANID_CONSTRAINTS;

        assertThrows(IllegalValueException.class, expectedMessage, xmlAdaptedLoanId::toModelType);
    }

    @Test
    public void equalityTest() {
        LoanId loanId = new LoanId("500");

        XmlAdaptedLoanId xmlAdaptedLoanId1 = new XmlAdaptedLoanId(loanId);
        XmlAdaptedLoanId xmlAdaptedLoanId2 = new XmlAdaptedLoanId(500);
        XmlAdaptedLoanId xmlAdaptedLoanId3 = new XmlAdaptedLoanId(2103);

        assertEquals(xmlAdaptedLoanId1, xmlAdaptedLoanId1); // Same instance
        assertEquals(xmlAdaptedLoanId1, xmlAdaptedLoanId2); // Same value
        assertNotEquals(xmlAdaptedLoanId1, xmlAdaptedLoanId3); // Different value
        assertNotEquals(xmlAdaptedLoanId3, "Different type");
    }

    @Test
    public void defaultConstructorTest() {
        XmlAdaptedLoanId xmlAdaptedLoanId = new XmlAdaptedLoanId(); // No exception should be thrown.
        assertEquals(xmlAdaptedLoanId, xmlAdaptedLoanId);
    }
}
