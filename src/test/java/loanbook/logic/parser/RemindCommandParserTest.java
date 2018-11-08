package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.logic.commands.CommandTestUtil.EMAILPW1_DESC;
import static loanbook.logic.commands.CommandTestUtil.PASSWORD1;
import static loanbook.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static loanbook.logic.parser.CliSyntax.PREFIX_ID;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import loanbook.logic.commands.RemindCommand;
import loanbook.model.loan.LoanId;

public class RemindCommandParserTest {
    private RemindCommandParser parser = new RemindCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + EMAILPW1_DESC + " " + PREFIX_ID + "1",
                new RemindCommand(PASSWORD1, new LoanId("1")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser,
                "pw/xncx -1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
    }
}
