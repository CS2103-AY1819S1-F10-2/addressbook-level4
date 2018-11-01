package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_DATE_RANGE;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static loanbook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import loanbook.logic.commands.SearchCommand;
import loanbook.model.loan.LoanTime;

public class SearchCommandParserTest {
    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_validDate_success() {
        isValidDate("2018-01-01 2018-01-02");
    }

    @Test
    public void parse_validSameDate_success() {
        isValidDate("2018-01-01 2018-01-01");
    }

    /**
     * Checks if the given input is a valid input for the search command.
     */
    private void isValidDate(String input) {

        String[] parts = input.trim().split(" ");

        String startDate = parts[0];
        String endDate = parts[1];

        LoanTime startLoanTime = LoanTime.startOfDayLoanTime(startDate);
        LoanTime endLoanTime = LoanTime.endOfDayLoanTime(endDate);

        assertParseSuccess(parser, input, new SearchCommand(startLoanTime, endLoanTime));
    }

    @Test
    public void parse_invalidStartDate_failure() {
        String input = "2018-001-01 2018-01-02";

        String expectedMessage = MESSAGE_INVALID_DATE_FORMAT;

        assertParseFailure(parser, input, expectedMessage);
    }

    @Test
    public void parse_invalidEndDate_failure() {
        String input = "2018-01-01 201-01-02";

        String expectedMessage = MESSAGE_INVALID_DATE_FORMAT;

        assertParseFailure(parser, input, expectedMessage);
    }

    @Test
    public void parse_invalidDate_failure() {
        String input = "hello World";

        String expectedMessage = MESSAGE_INVALID_DATE_FORMAT;

        assertParseFailure(parser, input, expectedMessage);
    }

    @Test
    public void parse_insufficientNumberOfInputs_failure() {
        String input = "2018-01-01";

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE);

        assertParseFailure(parser, input, expectedMessage);
    }

    @Test
    public void parse_excessiveNumberOfInputs_failure() {
        String input = "2018-01-01 2018-01-03 2018-01-03";

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE);

        assertParseFailure(parser, input, expectedMessage);
    }

    @Test
    public void parse_invalidRange_failure() {
        String input = "2018-01-02 2018-01-01";

        String expectedMessage = MESSAGE_INVALID_DATE_RANGE;

        assertParseFailure(parser, input, expectedMessage);
    }
}
