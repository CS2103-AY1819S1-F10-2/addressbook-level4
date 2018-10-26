package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.loan.LoanId;
import seedu.address.model.loan.LoanIdManager;

public class XmlAdaptedLoanIdManagerTest {

    @Test
    public void defaultConstructorTest() {
        XmlAdaptedLoanIdManager xmlManager = new XmlAdaptedLoanIdManager(); // No exception should be thrown.
        assertEquals(xmlManager, xmlManager);
    }

    @Test
    public void toModelTypeValidTest() throws IllegalValueException {
        // Construct from model's LoanIdManager.
        LoanIdManager modelManager1 = new LoanIdManager(LoanId.fromInt(500));
        XmlAdaptedLoanIdManager xmlManager1 = new XmlAdaptedLoanIdManager(modelManager1);

        // Construct from XmlAdaptedLoanId, exactly the same as above.
        XmlAdaptedLoanId xmlId = new XmlAdaptedLoanId(500);
        XmlAdaptedLoanIdManager xmlManager2 = new XmlAdaptedLoanIdManager(xmlId);

        assertEquals(modelManager1, xmlManager1.toModelType()); // Converting back into the original
        assertEquals(modelManager1, xmlManager2.toModelType()); // Converting from XML object to model

        // Construct a model LoanIdManager with no last used Loan ID (i.e. it starts from the initial Loan ID)
        LoanIdManager modelManager2 = new LoanIdManager();
        XmlAdaptedLoanIdManager xmlManager3 = new XmlAdaptedLoanIdManager(modelManager2);

        // Construct with null as the lastUsedLoanId. When converted to the model type, the output should be a
        // LoanIdManager that starts from the initial Loan ID.
        XmlAdaptedLoanIdManager xmlManager4 = new XmlAdaptedLoanIdManager((XmlAdaptedLoanId) null);

        assertEquals(modelManager2, xmlManager3.toModelType()); // Converting back into the original
        assertEquals(modelManager2, xmlManager4.toModelType()); // Converting from XML object to model

    }

    @Test
    public void toModelTypeInvalidTest() {
        XmlAdaptedLoanId invalidLoanId = new XmlAdaptedLoanId(-1); // Negative value
        XmlAdaptedLoanIdManager invalidLoanIdManager = new XmlAdaptedLoanIdManager(invalidLoanId);

        assertThrows(IllegalValueException.class, invalidLoanIdManager::toModelType);
    }

    @Test
    public void equalityTest() {
        LoanIdManager modelManager = new LoanIdManager(LoanId.fromInt(2103));
        XmlAdaptedLoanId xmlId1 = new XmlAdaptedLoanId(2103);
        XmlAdaptedLoanId xmlId2 = new XmlAdaptedLoanId(9001);

        XmlAdaptedLoanIdManager xmlManager1 = new XmlAdaptedLoanIdManager(modelManager);
        XmlAdaptedLoanIdManager xmlManager2 = new XmlAdaptedLoanIdManager(xmlId1);
        XmlAdaptedLoanIdManager xmlManager3 = new XmlAdaptedLoanIdManager(xmlId2);

        assertEquals(xmlManager1, xmlManager1); // Same instance
        assertEquals(xmlManager1, xmlManager2); // Same value
        assertNotEquals(xmlManager2, xmlManager3); // Different last used Loan ID
        assertNotEquals(xmlManager2, "Different type");
    }

}
