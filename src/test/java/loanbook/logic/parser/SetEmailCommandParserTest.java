package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.logic.commands.CommandTestUtil.PASSWORD2;
import static loanbook.logic.commands.CommandTestUtil.PASSWORD2_DESC;
import static loanbook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static loanbook.logic.commands.CommandTestUtil.USER_EMAIL1_DESC;
import static loanbook.logic.commands.CommandTestUtil.VALID_USER_EMAIL1;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import loanbook.logic.commands.SetEmailCommand;
import loanbook.model.loan.Email;

public class SetEmailCommandParserTest {
    private SetEmailCommandParser parser = new SetEmailCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + USER_EMAIL1_DESC + PASSWORD2_DESC,
                new SetEmailCommand(new Email(VALID_USER_EMAIL1), PASSWORD2));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser,
                "dcs", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetEmailCommand.MESSAGE_USAGE));
    }
}
