package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.logic.parser.CliSyntax.PREFIX_BIKE;
import static loanbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static loanbook.logic.parser.CliSyntax.PREFIX_LOANRATE;
import static loanbook.logic.parser.CliSyntax.PREFIX_NAME;
import static loanbook.logic.parser.CliSyntax.PREFIX_NRIC;
import static loanbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static loanbook.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import loanbook.logic.commands.AddCommand;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Email;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanId;
import loanbook.model.loan.LoanRate;
import loanbook.model.loan.Name;
import loanbook.model.loan.Nric;
import loanbook.model.loan.Phone;
import loanbook.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    public static final LoanId PLACEHOLDER_LOAN_ID = LoanId.fromInt(999999999);

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NAME,
                        PREFIX_NRIC,
                        PREFIX_PHONE,
                        PREFIX_EMAIL,
                        PREFIX_BIKE,
                        PREFIX_LOANRATE,
                        PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap,
                PREFIX_NAME,
                PREFIX_NRIC,
                PREFIX_PHONE,
                PREFIX_EMAIL,
                PREFIX_BIKE,
                PREFIX_LOANRATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Bike bike = ParserUtil.parseBike(argMultimap.getValue(PREFIX_BIKE).get());
        LoanRate rate = ParserUtil.parseLoanRate(argMultimap.getValue(PREFIX_LOANRATE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Loan loanWithPlaceholderId = new Loan(PLACEHOLDER_LOAN_ID, name, nric, phone, email, bike, rate, tagList);

        return new AddCommand(loanWithPlaceholderId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
