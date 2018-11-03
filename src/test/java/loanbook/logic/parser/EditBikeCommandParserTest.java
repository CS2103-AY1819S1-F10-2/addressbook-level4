package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static loanbook.logic.commands.CommandTestUtil.NAME_DESC_BIKE1;
import static loanbook.logic.commands.CommandTestUtil.NAME_DESC_BIKE2;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE1;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE2;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import loanbook.logic.commands.EditBikeCommand;
import loanbook.logic.commands.EditBikeCommand.EditBikeDescriptor;
import loanbook.model.loan.Name;
import loanbook.testutil.EditBikeDescriptorBuilder;

public class EditBikeCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditBikeCommand.MESSAGE_USAGE);

    private EditBikeCommandParser parser = new EditBikeCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no bike name specified
        assertParseFailure(parser, "n/Bike002", MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "Bike001", EditBikeCommand.MESSAGE_NOT_EDITED);

        // no bike name and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid name
        assertParseFailure(parser, "B^ke&01" + NAME_DESC_BIKE1, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble: considered part of the bike's name
        assertParseFailure(parser, VALID_NAME_BIKE1 + " some random string",
                EditBikeCommand.MESSAGE_NOT_EDITED);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, VALID_NAME_BIKE1 + "i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, VALID_NAME_BIKE1 + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = VALID_NAME_BIKE1 + NAME_DESC_BIKE2;

        EditBikeDescriptor descriptor = new EditBikeDescriptorBuilder().withName(VALID_NAME_BIKE2).build();
        EditBikeCommand expectedCommand = new EditBikeCommand(new Name(VALID_NAME_BIKE1), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = VALID_NAME_BIKE1 + NAME_DESC_BIKE1 + NAME_DESC_BIKE2;

        EditBikeDescriptor descriptor = new EditBikeDescriptorBuilder().withName(VALID_NAME_BIKE2).build();
        EditBikeCommand expectedCommand = new EditBikeCommand(new Name(VALID_NAME_BIKE1), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        String userInput = VALID_NAME_BIKE1 + INVALID_NAME_DESC + NAME_DESC_BIKE2;

        EditBikeDescriptor descriptor = new EditBikeDescriptorBuilder().withName(VALID_NAME_BIKE2).build();
        EditBikeCommand expectedCommand = new EditBikeCommand(new Name(VALID_NAME_BIKE1), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
