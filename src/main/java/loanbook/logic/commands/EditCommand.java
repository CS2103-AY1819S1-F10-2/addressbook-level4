package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.logic.parser.CliSyntax.PREFIX_BIKE;
import static loanbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static loanbook.logic.parser.CliSyntax.PREFIX_LOANRATE;
import static loanbook.logic.parser.CliSyntax.PREFIX_NAME;
import static loanbook.logic.parser.CliSyntax.PREFIX_NRIC;
import static loanbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static loanbook.logic.parser.CliSyntax.PREFIX_TAG;
import static loanbook.model.Model.PREDICATE_SHOW_ALL_LOANS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import loanbook.commons.core.Messages;
import loanbook.commons.core.index.Index;
import loanbook.commons.util.CollectionUtil;
import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Email;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanRate;
import loanbook.model.loan.LoanStatus;
import loanbook.model.loan.LoanTime;
import loanbook.model.loan.Name;
import loanbook.model.loan.Nric;
import loanbook.model.loan.Phone;
import loanbook.model.tag.Tag;

/**
 * Edits the details of an existing loan in the loan book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the loan identified "
            + "by the index number used in the displayed loan list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_NRIC + "NRIC] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_BIKE + "BIKE] "
            + "[" + PREFIX_LOANRATE + "LOANRATE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_LOAN_SUCCESS = "Edited Loan: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_LOAN = "This loan already exists in the loan book.";
    public static final String MESSAGE_BIKE_NOT_FOUND = "No bike with that name exists within the loan book.";

    private final Index index;
    private final EditLoanDescriptor editLoanDescriptor;

    /**
     * @param index of the loan in the filtered loan list to edit
     * @param editLoanDescriptor details to edit the loan with
     */
    public EditCommand(Index index, EditLoanDescriptor editLoanDescriptor) {
        requireNonNull(index);
        requireNonNull(editLoanDescriptor);

        this.index = index;
        this.editLoanDescriptor = new EditLoanDescriptor(editLoanDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Loan> lastShownList = model.getFilteredLoanList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
        }

        Loan loanToEdit = lastShownList.get(index.getZeroBased());
        Loan editedLoan = createEditedLoan(loanToEdit, editLoanDescriptor, model);

        if (!loanToEdit.isSame(editedLoan) && model.hasLoan(editedLoan)) {
            throw new CommandException(MESSAGE_DUPLICATE_LOAN);
        }

        model.updateLoan(loanToEdit, editedLoan);
        model.updateFilteredLoanList(PREDICATE_SHOW_ALL_LOANS);
        model.commitLoanBook();
        return new CommandResult(String.format(MESSAGE_EDIT_LOAN_SUCCESS, editedLoan));
    }

    /**
     * Creates and returns a {@code Loan} with the details of {@code loanToEdit}
     * edited with {@code editLoanDescriptor}.
     */
    private static Loan createEditedLoan(
            Loan loanToEdit,
            EditLoanDescriptor editLoanDescriptor,
            Model model) throws CommandException {
        assert loanToEdit != null;

        Name updatedName = editLoanDescriptor.getName().orElse(loanToEdit.getName());
        Nric updatedNric = editLoanDescriptor.getNric().orElse(loanToEdit.getNric());
        Phone updatedPhone = editLoanDescriptor.getPhone().orElse(loanToEdit.getPhone());
        Email updatedEmail = editLoanDescriptor.getEmail().orElse(loanToEdit.getEmail());
        Bike updatedBike = getEditedBike(editLoanDescriptor, model).orElse(loanToEdit.getBike());
        LoanRate updatedRate = editLoanDescriptor.getLoanRate().orElse(loanToEdit.getLoanRate());
        LoanTime updatedStartTime = loanToEdit.getLoanStartTime();
        LoanTime updatedEndTime = loanToEdit.getLoanEndTime();
        Set<Tag> updatedTags = editLoanDescriptor.getTags().orElse(loanToEdit.getTags());
        LoanStatus updatedLoanStatus = editLoanDescriptor.getLoanStatus().orElse(loanToEdit.getLoanStatus());

        return new Loan(updatedName,
                updatedNric,
                updatedPhone,
                updatedEmail,
                updatedBike,
                updatedRate,
                updatedStartTime,
                updatedEndTime,
                updatedLoanStatus,
                updatedTags
        );
    }

    /**
     * Retrieves the edited bike from the {@code model}, if a
     * dummy bike was specified in the {@code editLoanDescriptor}. Returns {@code Optional.empty()} otherwise.
     * @throws CommandException if a dummy bike was specified but could not be found in the model.
     */
    private static Optional<Bike> getEditedBike(
            EditLoanDescriptor editLoanDescriptor,
            Model model) throws CommandException {

        Optional<Bike> dummyBike = editLoanDescriptor.getBike();
        if (dummyBike.isPresent()) {
            Optional<Bike> editedBike = model.getBike(dummyBike.get().getName().value);
            if (!editedBike.isPresent()) {
                throw new CommandException(MESSAGE_BIKE_NOT_FOUND);
            }
            return editedBike;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editLoanDescriptor.equals(e.editLoanDescriptor);
    }

    /**
     * Stores the details to edit the loan with. Each non-empty field value will replace the
     * corresponding field value of the loan.
     */
    public static class EditLoanDescriptor {
        private Name name;
        private Nric nric;
        private Phone phone;
        private Email email;
        private Bike bike;
        private LoanRate rate;
        private Set<Tag> tags;
        private LoanStatus loanStatus;

        public EditLoanDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditLoanDescriptor(EditLoanDescriptor toCopy) {
            setName(toCopy.name);
            setNric(toCopy.nric);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setBike(toCopy.bike);
            setLoanRate(toCopy.rate);
            setTags(toCopy.tags);
            setLoanStatus(toCopy.loanStatus);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, nric, phone, email, bike, rate, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setBike(Bike bike) {
            this.bike = bike;
        }

        public Optional<Bike> getBike() {
            return Optional.ofNullable(bike);
        }

        public void setLoanRate(LoanRate rate) {
            this.rate = rate;
        }

        public Optional<LoanRate> getLoanRate() {
            return Optional.ofNullable(rate);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setLoanStatus(LoanStatus loanStatus) {
            this.loanStatus = loanStatus;
        }

        public Optional<LoanStatus> getLoanStatus() {
            return Optional.ofNullable(loanStatus);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditLoanDescriptor)) {
                return false;
            }

            // state check
            EditLoanDescriptor e = (EditLoanDescriptor) other;

            return getName().equals(e.getName())
                    && getNric().equals(e.getNric())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getBike().equals(e.getBike())
                    && getLoanRate().equals(e.getLoanRate())
                    && getTags().equals(e.getTags());
        }
    }
}
