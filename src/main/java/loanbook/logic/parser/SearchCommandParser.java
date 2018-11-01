package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static loanbook.commons.core.Messages.MESSAGE_INVALID_DATE_RANGE;

import loanbook.logic.commands.SearchCommand;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.loan.LoanTime;

/**
 * Parses input arguments and creates a new SearchCommand object.
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns an SearchCommand object for execution.
     */
    public SearchCommand parse(String args) throws ParseException {
        String[] parts = args.trim().split(" ");

        if (parts.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        String startDate = parts[0];
        String endDate = parts[1];

        if (!LoanTime.isValidDate(startDate) || !LoanTime.isValidDate(endDate)) {
            throw new ParseException(MESSAGE_INVALID_DATE_FORMAT);
        }

        LoanTime startLoanTime = LoanTime.startOfDayLoanTime(startDate);
        LoanTime endLoanTime = LoanTime.endOfDayLoanTime(endDate);

        if (endLoanTime.isBefore(startLoanTime)) {
            throw new ParseException(MESSAGE_INVALID_DATE_RANGE);
        }

        return new SearchCommand(startLoanTime, endLoanTime);
    }
}
