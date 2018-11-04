package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.DESC_BIKE1;
import static loanbook.logic.commands.CommandTestUtil.DESC_BIKE2;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE2;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import loanbook.testutil.EditBikeDescriptorBuilder;

public class EditBikeDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditBikeCommand.EditBikeDescriptor descriptorWithSameValues =
                new EditBikeCommand.EditBikeDescriptor(DESC_BIKE1);
        assertTrue(DESC_BIKE1.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_BIKE1.equals(DESC_BIKE1));

        // null -> returns false
        assertFalse(DESC_BIKE1.equals(null));

        // different types -> returns false
        assertFalse(DESC_BIKE1.equals(5));

        // different values -> returns false
        assertFalse(DESC_BIKE1.equals(DESC_BIKE2));

        // different name -> returns false
        EditBikeCommand.EditBikeDescriptor editedAmy =
            new EditBikeDescriptorBuilder(DESC_BIKE1).withName(VALID_NAME_BIKE2).build();
        assertFalse(DESC_BIKE1.equals(editedAmy));
    }
}
