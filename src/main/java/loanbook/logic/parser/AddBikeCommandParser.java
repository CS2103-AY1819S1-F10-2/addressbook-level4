package loanbook.logic.parser;

import static loanbook.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import loanbook.logic.commands.AddBikeCommand;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Name;

/**
 * Parses input arguments and creates a new AddBikeCommand object.
 */
public class AddBikeCommandParser extends ArgumentParser<AddBikeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddBikeCommand
     * and returns an AddBikeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddBikeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = getArgumentMultimap(args,
                List.of(PREFIX_NAME),
                List.of(),
                AddBikeCommand.MESSAGE_USAGE);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        Bike bike = new Bike(name);

        return new AddBikeCommand(bike);
    }

}
