package seedu.address.model.loan;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Loan in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Loan {

    // Loaner identity fields
    private final Name loanerName;
    private final Phone loanerPhone;
    private final Email loanerEmail;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Loan(Name loanerName, Phone loanerPhone, Email loanerEmail, Address address, Set<Tag> tags) {
        requireAllNonNull(loanerName, loanerPhone, loanerEmail, address, tags);
        this.loanerName = loanerName;
        this.loanerPhone = loanerPhone;
        this.loanerEmail = loanerEmail;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getLoanerName() {
        return loanerName;
    }

    public Phone getLoanerPhone() {
        return loanerPhone;
    }

    public Email getLoanerEmail() {
        return loanerEmail;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both loans of the same loanerName have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two loans.
     */
    public boolean isSameLoan(Loan otherLoan) {
        if (otherLoan == this) {
            return true;
        }

        return otherLoan != null
                && otherLoan.getLoanerName().equals(getLoanerName())
                && (otherLoan.getLoanerPhone().equals(getLoanerPhone()) || otherLoan.getLoanerEmail().equals(getLoanerEmail()));
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
        return otherLoan.getLoanerName().equals(getLoanerName())
                && otherLoan.getLoanerPhone().equals(getLoanerPhone())
                && otherLoan.getLoanerEmail().equals(getLoanerEmail())
                && otherLoan.getAddress().equals(getAddress())
                && otherLoan.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(loanerName, loanerPhone, loanerEmail, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getLoanerName())
                .append(" Phone: ").append(getLoanerPhone())
                .append(" Email: ").append(getLoanerEmail())
                .append(" Address: ").append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
