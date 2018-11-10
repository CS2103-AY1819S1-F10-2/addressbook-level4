package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.logic.parser.CliSyntax.PREFIX_NAME;
import static loanbook.model.Model.PREDICATE_SHOW_ALL_BIKES;

import java.util.Optional;

import loanbook.commons.util.CollectionUtil;
import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Name;

/**
 * Edits the details of an existing bike in the loan book.
 */
public class EditBikeCommand extends Command {

    public static final String COMMAND_WORD = "editbike";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the bike with the given name. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: NAME "
        + "[" + PREFIX_NAME + "NAME]\n"
        + "Example: " + COMMAND_WORD + " Bike001 "
        + PREFIX_NAME + "Bike002";

    public static final String MESSAGE_EDIT_BIKE_SUCCESS = "Edited bike: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_BIKE_NOT_FOUND = "No bike with that name exists within the loan book.";

    private final Name bikeName;
    private final EditBikeDescriptor editBikeDescriptor;

    /**
     * @param bikeName The name of the bike in the filtered bike list to edit
     * @param editBikeDescriptor details to edit the bike with
     */
    public EditBikeCommand(Name bikeName, EditBikeDescriptor editBikeDescriptor) {
        requireNonNull(bikeName);
        requireNonNull(editBikeDescriptor);

        this.bikeName = bikeName;
        this.editBikeDescriptor = new EditBikeDescriptor(editBikeDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        Optional<Bike> bikeToEdit = model.getBike(bikeName.value);
        if (!bikeToEdit.isPresent()) {
            throw new CommandException(MESSAGE_BIKE_NOT_FOUND);
        }

        Bike editedBike = createEditedBike(bikeToEdit.get(), editBikeDescriptor, model);

        model.updateBike(bikeToEdit.get(), editedBike);
        model.updateFilteredBikeList(PREDICATE_SHOW_ALL_BIKES);
        model.commitLoanBook();
        return new CommandResult(String.format(MESSAGE_EDIT_BIKE_SUCCESS, editedBike));
    }

    /**
     * Creates and returns a {@code Bike} with the details of {@code bikeToEdit}
     * edited with {@code editBikeDescriptor}.
     */
    private static Bike createEditedBike(
        Bike bikeToEdit,
        EditBikeDescriptor editBikeDescriptor,
        Model model) throws CommandException {
        assert bikeToEdit != null;

        Name updatedName = editBikeDescriptor.getName().orElse(bikeToEdit.getName());

        return new Bike(
            updatedName
        );
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditBikeCommand)) {
            return false;
        }

        // state check
        EditBikeCommand e = (EditBikeCommand) other;
        return bikeName.equals(e.bikeName)
            && editBikeDescriptor.equals(e.editBikeDescriptor);
    }

    /**
     * Stores the details to edit the bike with. Each non-empty field value will replace the
     * corresponding field value of the bike.
     */
    public static class EditBikeDescriptor {
        private Name name;

        public EditBikeDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBikeDescriptor(EditBikeDescriptor toCopy) {
            setName(toCopy.name);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditBikeDescriptor)) {
                return false;
            }

            // state check
            EditBikeDescriptor e = (EditBikeDescriptor) other;

            return getName().equals(e.getName());
        }
    }
}
