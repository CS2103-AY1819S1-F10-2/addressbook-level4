package seedu.address.model.loan;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.bike.Bike;
import seedu.address.model.tag.Tag;

/**
 * Represents a Loan in the loan book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Loan {

    // Identity fields
    private final Bike bike;
    private final Name name;
    private final Nric nric;
    //TODO: add LoanId filed
    //private final LoanId id;

    // Data fields
    private final LoanRate rate;
    private final LoanTime time;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Loan(Name name,
                Nric nric,
                Phone phone,
                Email email,
                Address address,
                Bike bike,
                LoanRate rate,
                LoanTime time,
                Set<Tag> tags) {
        requireAllNonNull(name, nric, phone, email, address, bike, rate, time, tags);
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.bike = bike;
        this.rate = rate;
        this.time = time;
        this.tags.addAll(tags);
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

    public Nric getNric() {
        return nric;
    }

    public Bike getBike() {
        return bike;
    }

    public LoanRate getLoanRate() {
        return rate;
    }

    public LoanTime getLoanTime() {
        return time;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
                && otherLoan.getNric().equals(getNric())
                && otherLoan.getBike().equals(getBike())
                && (otherLoan.getEmail().equals(getEmail()) || otherLoan.getPhone().equals(getPhone())
                || otherLoan.getLoanRate().equals(getLoanRate()) || otherLoan.getLoanTime().equals(getLoanTime()));
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
                && otherLoan.getNric().equals(getNric())
                && otherLoan.getPhone().equals(getPhone())
                && otherLoan.getEmail().equals(getEmail())
                && otherLoan.getAddress().equals(getAddress())
                && otherLoan.getBike().equals(getBike())
                && otherLoan.getLoanRate().equals(getLoanRate())
                && otherLoan.getLoanTime().equals(getLoanTime())
                && otherLoan.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, nric, phone, email, address, bike, rate, time, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Nric: ")
                .append(getNric())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Bike: ")
                .append(getBike())
                .append(" LoanRate: ")
                .append(getLoanRate())
                .append(" LoanTime: ")
                .append(getLoanTime())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
