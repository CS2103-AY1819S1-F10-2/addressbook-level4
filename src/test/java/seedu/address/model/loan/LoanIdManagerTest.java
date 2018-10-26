package seedu.address.model.loan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.Test;

public class LoanIdManagerTest {

    private static final int EXPECTED_INITIAL_ID_VALUE = 0;
    private static final int EXPECTED_MAXIMUM_ID_VALUE = 999999999;

    private static final LoanId EXPECTED_INITIAL_LOAN_ID = LoanId.fromInt(EXPECTED_INITIAL_ID_VALUE);
    private static final LoanId EXPECTED_MAXIMUM_LOAN_ID = LoanId.fromInt(EXPECTED_MAXIMUM_ID_VALUE);

    @Test
    public void defaultIdManagerConstructorTest() {
        // Create new Loan ID Manager starting from the initial ID value.
        LoanIdManager idManager = new LoanIdManager();
        assertNull(idManager.getLastUsedLoanId());
        assertTrue(idManager.hasNextAvailableLoanId()); // There should be a next Loan ID

        LoanId nextAvailableLoanId = idManager.getNextAvailableLoanId();
        assertEquals(EXPECTED_INITIAL_LOAN_ID, nextAvailableLoanId); // The first Loan ID created should be 0.
        assertEquals(EXPECTED_INITIAL_LOAN_ID, idManager.getLastUsedLoanId());
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void runningIdFromDefaultConstructorTest() {
        LoanIdManager idManager = new LoanIdManager();

        // The IDs returned by the idManager should be {0, 1, 2, ... 9}
        for (int id = EXPECTED_INITIAL_ID_VALUE; id < 10; ++id) {
            LoanId expectedLoanId = LoanId.fromInt(id);
            LoanId actualLoanId = idManager.getNextAvailableLoanId();

            assertEquals(expectedLoanId, actualLoanId);
            assertEquals(expectedLoanId, idManager.getLastUsedLoanId());
            assertEquals(id, (int) actualLoanId.value);
        }
    }

    @Test
    public void constructorWithSuppliedLastUsedLoanId() {
        LoanId lastUsedLoanId = LoanId.fromInt(500);

        LoanIdManager idManager = new LoanIdManager(lastUsedLoanId);
        assertTrue(idManager.hasNextAvailableLoanId()); // 500 is not the maximum; there should be a next Loan ID
        assertEquals(lastUsedLoanId, idManager.getLastUsedLoanId());

        // The next ID should be 501.
        LoanId nextAvailableLoanId = idManager.getNextAvailableLoanId();
        LoanId expectedLoanId = LoanId.fromInt(501);

        assertEquals(expectedLoanId, nextAvailableLoanId);
        assertEquals(expectedLoanId, idManager.getLastUsedLoanId());
        assertEquals(501, (int) nextAvailableLoanId.value);
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void runningIdFromLastUsedLoanIdTest() {
        int lastUsedIdValue = 2100;
        LoanId lastUsedLoanId = LoanId.fromInt(lastUsedIdValue);

        LoanIdManager idManager = new LoanIdManager(lastUsedLoanId);

        // The IDs returned should be {2101, 2102, ... , 2109}
        for (int id = lastUsedIdValue + 1; id < 2110; ++id) {
            LoanId expectedLoanId = LoanId.fromInt(id);
            LoanId actualLoanId = idManager.getNextAvailableLoanId();

            assertEquals(expectedLoanId, actualLoanId);
            assertEquals(expectedLoanId, idManager.getLastUsedLoanId());
            assertEquals(id, (int) actualLoanId.value);
        }
    }

    @Test
    public void constructorWithNullLastUsedIdTest() {
        LoanIdManager idManagerFromNullId = new LoanIdManager(null);
        LoanIdManager idManagerFromInitialId = new LoanIdManager();

        assertEquals(idManagerFromInitialId, idManagerFromNullId);

        LoanId expectedLoanId = LoanId.fromInt(0);
        LoanId actualLoanId = idManagerFromNullId.getNextAvailableLoanId();

        assertEquals(expectedLoanId, actualLoanId);
        assertEquals(0, (int) actualLoanId.value);
    }

    @Test
    public void maximumLoanIdTest() {
        // Create an ID manager where the last used ID is the maximum ID.
        LoanIdManager idManager = new LoanIdManager(EXPECTED_MAXIMUM_LOAN_ID);

        assertFalse(idManager.hasNextAvailableLoanId());
        assertEquals(EXPECTED_MAXIMUM_LOAN_ID, idManager.getLastUsedLoanId());
        assertThrows(IllegalStateException.class, idManager::getNextAvailableLoanId);
    }

    @Test
    public void incrementToMaximumTest() {
        int offset = 10;
        int initialValue = EXPECTED_MAXIMUM_ID_VALUE - offset;

        LoanIdManager idManager = new LoanIdManager(LoanId.fromInt(initialValue));

        // With an offset of 10, the ID manager should
        // reach the maximum ID after 10 calls
        for (int i = 1; i <= offset; ++i) {
            int expectedIdValue = initialValue + i;
            LoanId expectedLoanId = LoanId.fromInt(expectedIdValue);
            LoanId actualLoanId = idManager.getNextAvailableLoanId();

            // Value check
            assertEquals(expectedLoanId, actualLoanId);
            assertEquals(expectedLoanId, idManager.getLastUsedLoanId());
            assertEquals(expectedIdValue, (int) actualLoanId.value);

            // Maximum check
            if (i < offset) {
                // Not yet the maximum. There should be more.
                assertTrue(idManager.hasNextAvailableLoanId());
                assertFalse(actualLoanId.isMaximumId());
            } else {
                // At the last iteration, the maximum loan ID should have been used.
                assertFalse(idManager.hasNextAvailableLoanId());
                assertTrue(actualLoanId.isMaximumId());
            }
        }
    }

    @Test
    public void equalityTest() {
        LoanIdManager idManagerFromInitialId1 = new LoanIdManager(); // An ID Manager with no last used ID
        LoanIdManager idManagerFromInitialId2 = new LoanIdManager(); // Same as above

        // An ID Manager with "2103" as the last used ID
        LoanIdManager idManagerFromLastUsedId1 = new LoanIdManager(LoanId.fromInt(2103));
        // An ID Manager with "2104" as the last used ID
        LoanIdManager idManagerFromLastUsedId2 = new LoanIdManager(LoanId.fromInt(2104));

        // An ID Manager with the maximum ID as the last used ID
        LoanIdManager idManagerWithMaxedId = new LoanIdManager(EXPECTED_MAXIMUM_LOAN_ID);
        // An ID Manager one call away from reaching the maximum ID
        LoanIdManager idManagerWithOneFromMaxId = new LoanIdManager(LoanId.fromInt(EXPECTED_MAXIMUM_ID_VALUE - 1));

        assertEquals(idManagerFromInitialId1, idManagerFromInitialId1); // Same instance
        assertEquals(idManagerFromInitialId1, idManagerFromInitialId2); // Both managers start from the initial ID

        assertNotEquals(idManagerFromInitialId1, idManagerFromLastUsedId1); // Different last used IDs
        assertNotEquals(idManagerFromLastUsedId1, idManagerFromInitialId1); // Commutativity, in case of Null Pointers

        assertNotEquals(idManagerFromLastUsedId1, idManagerFromLastUsedId2); // Different last used IDs
        idManagerFromLastUsedId1.getNextAvailableLoanId(); // Increment to next value.
        assertEquals(idManagerFromLastUsedId1, idManagerFromLastUsedId2); // The last used IDs should now be the same.

        assertNotEquals(idManagerWithMaxedId, idManagerWithOneFromMaxId); // One is maximized; the other is not.
        idManagerWithOneFromMaxId.getNextAvailableLoanId(); // Increment to next value.
        assertEquals(idManagerWithMaxedId, idManagerWithOneFromMaxId); // Both should be maximized now.

        assertNotEquals(idManagerFromInitialId1, "Different type");
    }

    @Test
    public void setFromExistingManagerTest() {
        LoanIdManager existingIdManager = new LoanIdManager(LoanId.fromInt(500));
        LoanIdManager managerToSet = new LoanIdManager(LoanId.fromInt(2103));

        assertEquals(LoanId.fromInt(2104), managerToSet.getNextAvailableLoanId());

        managerToSet.setFromExistingManager(existingIdManager);

        assertEquals(LoanId.fromInt(501), managerToSet.getNextAvailableLoanId());
        assertEquals(LoanId.fromInt(500), existingIdManager.getLastUsedLoanId());

    }
}
