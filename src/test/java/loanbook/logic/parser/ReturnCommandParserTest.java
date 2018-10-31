package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import loanbook.commons.core.index.Index;
import loanbook.logic.commands.FindCommand;
import loanbook.logic.commands.ReturnCommand;

public class ReturnCommandParserTest {

    private ReturnCommandParser parser = new ReturnCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ReturnCommand expectedFindCommand =
                new ReturnCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, "1", expectedFindCommand);
    }

}
