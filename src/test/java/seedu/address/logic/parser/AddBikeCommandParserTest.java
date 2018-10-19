package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BIKE1;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BIKE2;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BIKE2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalBikes.BIKE2;

import org.junit.Test;

import seedu.address.logic.commands.AddBikeCommand;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Name;
import seedu.address.testutil.BikeBuilder;

public class AddBikeCommandParserTest {
    private AddBikeCommandParser parser = new AddBikeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Bike expectedBike = new BikeBuilder(BIKE2).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BIKE2, new AddBikeCommand(expectedBike));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_BIKE1 + NAME_DESC_BIKE2, new AddBikeCommand(expectedBike));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBikeCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BIKE2, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BIKE2,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBikeCommand.MESSAGE_USAGE));
    }
}
