package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import loanbook.logic.commands.SearchCommand;
import loanbook.logic.parser.exceptions.ParseException;

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

        if (false) { //TODO CHECK IF BOTH DATES ARE VALID
            throw new ParseException("DATE PROVIDED IS IN AN INVALID FORMAT. PLEASE USE THE FORMAT DD-MM-YY.");
        }

        return new SearchCommand(startDate, endDate);
    }
}
