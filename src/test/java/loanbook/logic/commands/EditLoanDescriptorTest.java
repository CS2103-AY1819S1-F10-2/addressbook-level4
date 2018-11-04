package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.DESC_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_LOANRATE_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE2;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import loanbook.testutil.EditLoanDescriptorBuilder;

public class EditLoanDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditLoanDescriptor descriptorWithSameValues = new EditCommand.EditLoanDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY == null);

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCommand.EditLoanDescriptor editedAmy =
            new EditLoanDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different NRIC -> returns false
        editedAmy = new EditLoanDescriptorBuilder(DESC_AMY).withNric(VALID_NRIC_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditLoanDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditLoanDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different bike -> returns false
        editedAmy = new EditLoanDescriptorBuilder(DESC_AMY).withBike(VALID_NAME_BIKE2).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different rate -> returns false
        editedAmy = new EditLoanDescriptorBuilder(DESC_AMY).withLoanRate(VALID_LOANRATE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditLoanDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
