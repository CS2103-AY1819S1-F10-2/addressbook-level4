package loanbook.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import loanbook.commons.core.ComponentManager;
import loanbook.commons.core.LogsCenter;
import loanbook.logic.commands.Command;
import loanbook.logic.commands.CommandResult;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.logic.parser.LoanBookParser;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.Model;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Loan;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final LoanBookParser loanBookParser;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        loanBookParser = new LoanBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = loanBookParser.parseCommand(commandText);
            return command.execute(model, history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Bike> getFilteredBikeList() {
        return model.getFilteredBikeList();
    }

    @Override
    public ObservableList<Loan> getFilteredLoanList() {
        return model.getFilteredLoanList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
