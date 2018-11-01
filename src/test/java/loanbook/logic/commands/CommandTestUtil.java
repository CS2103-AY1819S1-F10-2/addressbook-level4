package loanbook.logic.commands;

import static loanbook.logic.parser.CliSyntax.PREFIX_BIKE;
import static loanbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static loanbook.logic.parser.CliSyntax.PREFIX_LOANRATE;
import static loanbook.logic.parser.CliSyntax.PREFIX_NAME;
import static loanbook.logic.parser.CliSyntax.PREFIX_NRIC;
import static loanbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static loanbook.logic.parser.CliSyntax.PREFIX_TAG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import loanbook.commons.core.index.Index;
import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.LoanBook;
import loanbook.model.Model;
import loanbook.model.loan.Loan;
import loanbook.model.loan.NameContainsKeywordsPredicate;
import loanbook.testutil.EditLoanDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NRIC_AMY = "F1234567N";
    public static final String VALID_NRIC_BOB = "S1234567D";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_LOANRATE_AMY = "0.15";
    public static final String VALID_LOANRATE_BOB = "12.5";
    public static final String VALID_LOANSTARTTIME_AMY = "12:32";
    public static final String VALID_LOANSTARTTIME_BOB = "2018-10-10 12:30";
    public static final String VALID_LOANENDTIME_AMY = "14:58";
    public static final String VALID_LOANENDTIME_BOB = "2018-10-10 14:59";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_NAME_BIKE1 = "BIKE001";
    public static final String VALID_NAME_BIKE2 = "BIKE002";
    public static final String VALID_NAME_BIKE3 = "Silver Surfer";
    public static final String VALID_NAME_BIKE4 = "Blue Ocean";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String BIKE_DESC_AMY = " " + PREFIX_BIKE + VALID_NAME_BIKE1;
    public static final String BIKE_DESC_BOB = " " + PREFIX_BIKE + VALID_NAME_BIKE2;
    public static final String LOANRATE_DESC_AMY = " " + PREFIX_LOANRATE + VALID_LOANRATE_AMY;
    public static final String LOANRATE_DESC_BOB = " " + PREFIX_LOANRATE + VALID_LOANRATE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String NAME_DESC_BIKE1 = " " + PREFIX_NAME + VALID_NAME_BIKE1;
    public static final String NAME_DESC_BIKE2 = " " + PREFIX_NAME + VALID_NAME_BIKE2;
    public static final String NAME_DESC_BIKE3 = " " + PREFIX_NAME + VALID_NAME_BIKE3;
    public static final String NAME_DESC_BIKE4 = " " + PREFIX_NAME + VALID_NAME_BIKE4;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "S*055310A"; // wrong format
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_BIKE_DESC = " " + PREFIX_BIKE + "***BIKE"; // '*' not allowed in bike names
    public static final String INVALID_LOANRATE_DESC = " " + PREFIX_LOANRATE + "1.4444"; // 'no more than 2 decimals
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditLoanDescriptor DESC_AMY;
    public static final EditCommand.EditLoanDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditLoanDescriptorBuilder().withName(VALID_NAME_AMY)
                .withNric(VALID_NRIC_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withBike(VALID_NAME_BIKE1)
                .withLoanRate(VALID_LOANRATE_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditLoanDescriptorBuilder().withName(VALID_NAME_BOB)
                .withNric(VALID_NRIC_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withBike(VALID_NAME_BIKE2)
                .withLoanRate(VALID_LOANRATE_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the loan book and the filtered loan list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        LoanBook expectedLoanBook = new LoanBook(actualModel.getLoanBook());
        List<Loan> expectedFilteredList = new ArrayList<>(actualModel.getFilteredLoanList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedLoanBook, actualModel.getLoanBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredLoanList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the loan at the given {@code targetIndex} in the
     * {@code model}'s loan book.
     */
    public static void showLoanAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredLoanList().size());

        Loan loan = model.getFilteredLoanList().get(targetIndex.getZeroBased());
        final String[] splitName = loan.getName().value.split("\\s+");
        model.updateFilteredLoanList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredLoanList().size());
    }

    /**
     * Deletes the first loan in {@code model}'s filtered list from {@code model}'s loan book.
     */
    public static void deleteFirstLoan(Model model) {
        Loan firstLoan = model.getFilteredLoanList().get(0);
        model.deleteLoan(firstLoan);
        model.commitLoanBook();
    }

}
