package loanbook.logic;

import javafx.collections.ObservableList;
import loanbook.logic.commands.CommandResult;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Loan;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of the filtered list of bikes */
    ObservableList<Bike> getFilteredBikeList();

    /** Returns an unmodifiable view of the filtered list of loans */
    ObservableList<Loan> getFilteredLoanList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
