package seedu.address.model.loan;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.loan.exceptions.SameLoanStatusException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Loan in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Loan {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private LoanStatus loanStatus;

    /**
     * Every field must be present and not null.
     * Old constructor that does not take into account the LoanStatus.
     */
    public Loan(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);

        // Initialise the loan to be ongoing.
        this.loanStatus = LoanStatus.ONGOING;
    }

    /**
     * Every field must be present and not null.
     * This constructor takes into account the loanStatus
     */
    public Loan(Name name, Phone phone, Email email, Address address, Set<Tag> tags, LoanStatus loanStatus) {
        requireAllNonNull(name, phone, email, address, tags, loanStatus);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.loanStatus = loanStatus;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Change the loan status to the newStatus as provided.
     * Throws SameLoanStatusException if the newStatus is the same as the previous status.
     * @param newStatus
     * @return true if the function managed to complete.
     * @throws SameLoanStatusException
     */
    public boolean changeLoanStatus(LoanStatus newStatus) throws SameLoanStatusException {
        if (loanStatus.equals(newStatus)) {
            throw new SameLoanStatusException();
        }
        else {
            loanStatus = newStatus;
            return true;
        }
    }

    /**
     * Returns true if both loans of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two loans.
     */
    public boolean isSameLoan(Loan otherLoan) {
        if (otherLoan == this) {
            return true;
        }

        return otherLoan != null
                && otherLoan.getName().equals(getName())
                && (otherLoan.getPhone().equals(getPhone()) || otherLoan.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both loans have the same identity and data fields.
     * This defines a stronger notion of equality between two loans.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Loan)) {
            return false;
        }

        Loan otherLoan = (Loan) other;
        return otherLoan.getName().equals(getName())
                && otherLoan.getPhone().equals(getPhone())
                && otherLoan.getEmail().equals(getEmail())
                && otherLoan.getAddress().equals(getAddress())
                && otherLoan.getLoanStatus().equals(getLoanStatus())
                && otherLoan.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, loanStatus);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Status: ")
                .append(getLoanStatus())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
