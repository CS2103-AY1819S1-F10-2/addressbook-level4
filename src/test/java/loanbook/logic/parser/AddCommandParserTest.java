package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static loanbook.logic.commands.CommandTestUtil.BIKE_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.BIKE_DESC_BOB;
import static loanbook.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static loanbook.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static loanbook.logic.commands.CommandTestUtil.INVALID_BIKE_DESC;
import static loanbook.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static loanbook.logic.commands.CommandTestUtil.INVALID_LOANRATE_DESC;
import static loanbook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static loanbook.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static loanbook.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static loanbook.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static loanbook.logic.commands.CommandTestUtil.LOANRATE_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.LOANRATE_DESC_BOB;
import static loanbook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static loanbook.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static loanbook.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static loanbook.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static loanbook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static loanbook.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static loanbook.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static loanbook.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static loanbook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static loanbook.testutil.TypicalLoans.AMY;
import static loanbook.testutil.TypicalLoans.BOB;

import org.junit.Test;

import loanbook.logic.commands.AddCommand;
import loanbook.model.loan.Email;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanRate;
import loanbook.model.loan.Name;
import loanbook.model.loan.Nric;
import loanbook.model.loan.Phone;
import loanbook.model.tag.Tag;
import loanbook.testutil.LoanBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Loan expectedLoan = new LoanBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedLoan));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedLoan));

        // multiple nrics - last nric accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_AMY + NRIC_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedLoan));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedLoan));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedLoan));

        // multiple bikes - last bike accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BIKE_DESC_AMY + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedLoan));

        // multiple rates - last rate accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BIKE_DESC_BOB + LOANRATE_DESC_AMY + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedLoan));

        // multiple times - last time accepted
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedLoan));

        // multiple tags - all accepted
        Loan expectedLoanMultipleTags = new LoanBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedLoanMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Loan expectedLoan = new LoanBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY + BIKE_DESC_AMY + LOANRATE_DESC_AMY,
                new AddCommand(expectedLoan));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid nric
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_NRIC_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Nric.MESSAGE_NRIC_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ADDRESS_DESC_BOB + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid bike
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_BIKE_DESC + LOANRATE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid rate
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BIKE_DESC_BOB + INVALID_LOANRATE_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, LoanRate.MESSAGE_LOANRATE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + NRIC_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC + INVALID_BIKE_DESC + LOANRATE_DESC_BOB,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
