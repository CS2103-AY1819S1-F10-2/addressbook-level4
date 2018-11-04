package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import loanbook.logic.commands.Command;
import loanbook.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that parses a use input of only prefixed arguments
 * for use by a {@code Command} of type {@code T}.
 */
public abstract class ArgumentParser<T extends Command> implements Parser<T> {

    /**
     * Generates an ArgumentMultimap from the given argument String and data specifications.
     * @param argsString The String passed to the command, containing all the arguments.
     * @param requiredArgs An array of Prefixes representing required data fields.
     * @param optionalArgs An array of Prefixes representing optional data fields.
     * @param msgUsage The message informing the user of the correct command usage.
     * @return an ArgumentMultimap containing the data from {@code args}.
     * @throws ParseException if the command does not have all required arguments, or has a pre-amble.
     */
    protected ArgumentMultimap getArgumentMultimap(
            String argsString,
            List<Prefix> requiredArgs,
            List<Prefix> optionalArgs,
            String msgUsage) throws ParseException {

        List<Prefix> allArgs = new ArrayList();
        allArgs.addAll(requiredArgs);
        allArgs.addAll(optionalArgs);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, allArgs);

        if (!arePrefixesPresent(argMultimap, requiredArgs) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, msgUsage));
        }

        return argMultimap;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, List<Prefix> prefixes) {
        return prefixes.stream().allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
