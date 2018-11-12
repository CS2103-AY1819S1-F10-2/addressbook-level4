package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import loanbook.logic.commands.DeleteBikeCommand;
import loanbook.model.loan.Name;

public class DeleteBikeCommandParserTest {

    private DeleteBikeCommandParser parser = new DeleteBikeCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteBikeCommand() {
        assertParseSuccess(parser, " n/Bike001 x/a12345",
                new DeleteBikeCommand(new Name("Bike001"), "a12345"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBikeCommand.MESSAGE_USAGE));
    }
}
