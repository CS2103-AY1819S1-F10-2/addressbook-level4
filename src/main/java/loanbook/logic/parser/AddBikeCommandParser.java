package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import loanbook.logic.commands.AddBikeCommand;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Name;

/**
 * Parses input arguments and creates a new AddBikeCommand object
 */
public class AddBikeCommandParser implements Parser<AddBikeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddBikeCommand
     * and returns an AddBikeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBikeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBikeCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        Bike bike = new Bike(name);

        return new AddBikeCommand(bike);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
