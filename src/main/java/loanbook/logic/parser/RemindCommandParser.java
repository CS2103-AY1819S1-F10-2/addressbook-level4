package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.logic.parser.CliSyntax.PREFIX_BIKE;
import static loanbook.logic.parser.CliSyntax.PREFIX_NAME;
import static loanbook.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.stream.Stream;

import loanbook.logic.commands.RemindCommand;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Name;

/**
 * Parses input arguments and creates a new RemindCommand object.
 */
public class RemindCommandParser implements Parser<RemindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemindCommand
     * and returns an RemindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PASSWORD, PREFIX_NAME, PREFIX_BIKE);

        if (!arePrefixesPresent(argMultimap, PREFIX_PASSWORD, PREFIX_NAME, PREFIX_BIKE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
        }

        String emailPassword = argMultimap.getValue(PREFIX_PASSWORD).get();
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Bike bike = ParserUtil.parseBike(argMultimap.getValue(PREFIX_BIKE).get());

        return new RemindCommand(emailPassword, name, bike);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
