package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LOANS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.loan.Address;
import seedu.address.model.loan.Email;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.Name;
import seedu.address.model.loan.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing loan in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the loan identified "
            + "by the index number used in the displayed loan list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_LOAN_SUCCESS = "Edited Loan: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_LOAN = "This loan already exists in the address book.";

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
        Loan editedLoan = createEditedLoan(loanToEdit, editLoanDescriptor);

        if (!loanToEdit.isSameLoan(editedLoan) && model.hasLoan(editedLoan)) {
            throw new CommandException(MESSAGE_DUPLICATE_LOAN);
        }

        model.updateLoan(loanToEdit, editedLoan);
        model.updateFilteredLoanList(PREDICATE_SHOW_ALL_LOANS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_LOAN_SUCCESS, editedLoan));
    }

    /**
     * Creates and returns a {@code Loan} with the details of {@code loanToEdit}
     * edited with {@code editLoanDescriptor}.
     */
    private static Loan createEditedLoan(Loan loanToEdit, EditLoanDescriptor editLoanDescriptor) {
        assert loanToEdit != null;

        Name updatedName = editLoanDescriptor.getName().orElse(loanToEdit.getName());
        Phone updatedPhone = editLoanDescriptor.getPhone().orElse(loanToEdit.getPhone());
        Email updatedEmail = editLoanDescriptor.getEmail().orElse(loanToEdit.getEmail());
        Address updatedAddress = editLoanDescriptor.getAddress().orElse(loanToEdit.getAddress());
        Set<Tag> updatedTags = editLoanDescriptor.getTags().orElse(loanToEdit.getTags());

        return new Loan(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
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
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditLoanDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditLoanDescriptor(EditLoanDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
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

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
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
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
