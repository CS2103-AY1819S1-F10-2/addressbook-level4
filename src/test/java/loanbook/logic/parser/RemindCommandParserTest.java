package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.logic.commands.CommandTestUtil.BIKE_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.PASSWORD1;
import static loanbook.logic.commands.CommandTestUtil.PASSWORD1_DESC;
import static loanbook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE1;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import loanbook.logic.commands.RemindCommand;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Name;

public class RemindCommandParserTest {
    private RemindCommandParser parser = new RemindCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + PASSWORD1_DESC + NAME_DESC_AMY + BIKE_DESC_AMY,
                new RemindCommand(PASSWORD1, new Name(VALID_NAME_AMY), new Bike(new Name(VALID_NAME_BIKE1))));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser,
                "xncx n/cn b/df", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
    }
}
