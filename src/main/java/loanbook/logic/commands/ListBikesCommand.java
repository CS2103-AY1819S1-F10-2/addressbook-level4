package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.model.Model.PREDICATE_SHOW_ALL_BIKES;

import loanbook.commons.core.EventsCenter;
import loanbook.commons.events.ui.BikeListShowEvent;
import loanbook.logic.CommandHistory;
import loanbook.model.Model;

/**
 * Lists all bikes in the loan book to the user.
 */
public class ListBikesCommand extends Command {

    public static final String COMMAND_WORD = "listbikes";

    public static final String MESSAGE_SUCCESS = "Listed all bikes";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredBikeList(PREDICATE_SHOW_ALL_BIKES);
        EventsCenter.getInstance().post(new BikeListShowEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
