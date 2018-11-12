package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import loanbook.logic.commands.ResetAllCommand;

public class ResetAllCommandParserTest {

    private static final String VALID_PASSWORD_STRING = "a12345";

    private ResetAllCommandParser parser = new ResetAllCommandParser();

    @Test
    public void parse_validArgs_returnsResetAllCommand() {
        assertParseSuccess(parser, " x/" + VALID_PASSWORD_STRING, new ResetAllCommand(VALID_PASSWORD_STRING));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "no password", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ResetAllCommand.MESSAGE_USAGE));
    }
}
