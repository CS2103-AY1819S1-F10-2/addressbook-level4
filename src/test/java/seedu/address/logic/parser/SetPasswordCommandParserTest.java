package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LOAN;

import org.junit.Test;

import seedu.address.logic.commands.SetPasswordCommand;
import seedu.address.model.Password;

public class SetPasswordCommandParserTest {

    private SetPasswordCommandParser parser = new SetPasswordCommandParser("a12345 newpass1");

    @Test
    public void parse_validArgs_returnsSetPasswordCommand() {
        assertParseSuccess(parser, "1", new SetPasswordCommand(INDEX_FIRST_LOAN));
    }
}
