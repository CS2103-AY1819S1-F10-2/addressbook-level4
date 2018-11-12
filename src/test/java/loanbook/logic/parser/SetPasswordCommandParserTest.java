package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import loanbook.logic.commands.SetPasswordCommand;
import loanbook.model.Password;

public class SetPasswordCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPasswordCommand.MESSAGE_USAGE);

    private static final String MESSAGE_PASSWORD_FORMAT = Password.MESSAGE_PASSWORD_CONSTRAINTS;

    private SetPasswordCommandParser parser = new SetPasswordCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, "noNewPass", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPassword_failure() {
        assertParseFailure(parser, "old new", MESSAGE_PASSWORD_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = "oldPass newPass";

        SetPasswordCommand expectedCommand = new SetPasswordCommand("oldPass", "newPass");
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
