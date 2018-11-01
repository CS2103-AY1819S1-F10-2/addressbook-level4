package loanbook.model.loan;

import static loanbook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import loanbook.model.UniqueListItem;
import loanbook.model.bike.Bike;
import loanbook.model.tag.Tag;

/**
 * Represents a Loan in the loan book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Loan implements UniqueListItem<Loan> {

    // Identity fields
    private final Bike bike;
    private final Name name;
    private final Nric nric;
    private final LoanId id;

    // Data fields
    private final LoanRate rate;
    private final LoanTime startTime;
    private final LoanTime endTime; // Note that endTime can be null
    private final Phone phone;
    private final Email email;
    private final Set<Tag> tags = new HashSet<>();
    private final LoanStatus loanStatus;

    /**
     * Default constructor.
     * Every field except endTime must be present and not null.
     */
    public Loan(LoanId id,
                Name name,
                Nric nric,
                Phone phone,
                Email email,
                Bike bike,
                LoanRate rate,
                LoanTime startTime,
                LoanTime endTime,
                LoanStatus loanStatus,
                Set<Tag> tags) {
        // Note that endTime can be null. This loans in progress do not have an endTime.
        requireAllNonNull(id, name, nric, phone, email, bike, rate, startTime, tags);
        this.id = id;
        this.name = name;
        this.nric = nric;
        this.phone = phone;
        this.email = email;
        this.bike = bike;
        this.rate = rate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.loanStatus = loanStatus;
        this.tags.addAll(tags);
    }

    /**
     * Constructor for adding a loan on the spot.
     * Every field must be present and not null.
     */
    public Loan(LoanId id,
                Name name,
                Nric nric,
                Phone phone,
                Email email,
                Bike bike,
                LoanRate rate,
                Set<Tag> tags) {

        // Initialise the loan to be ongoing.
        this(id, name, nric, phone, email, bike, rate,
                new LoanTime(), null, LoanStatus.ONGOING, tags);
    }

    /**
     * Constructor when you know the start and end times.
     * If you know the end time, then the loan would be returned.
     * Every field except endTime must be present and not null.
     */
    public Loan(LoanId id,
                Name name,
                Nric nric,
                Phone phone,
                Email email,
                Bike bike,
                LoanRate rate,
                LoanTime startTime,
                LoanTime endTime,
                Set<Tag> tags) {
        this(id, name, nric, phone, email, bike, rate,
                startTime, endTime, LoanStatus.RETURNED, tags);
    }

    /**
     * Copies over an existing Loan and edits the Bike, for AddCommand.
     */
    public Loan(Loan other, Bike bike) {
        this(other.id,
                other.name,
                other.nric,
                other.phone,
                other.email,
                bike,
                other.rate,
                other.startTime,
                other.endTime,
                other.loanStatus,
                other.tags);
    }

    /**
     * Copies over an existing Loan and edits the endTime, for ReturnCommand.
     */
    public Loan(Loan other, LoanTime endTime) {
        this(other.id,
                other.name,
                other.nric,
                other.phone,
                other.email,
                other.bike,
                other.rate,
                other.startTime,
                endTime,
                other.loanStatus,
                other.tags);
    }

    public LoanId getLoanId() {
        return id;
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

    public LoanStatus getLoanStatus() {
        return loanStatus;
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

    public LoanTime getLoanStartTime() {
        return startTime;
    }

    public LoanTime getLoanEndTime() {
        return endTime;
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
     */
    @Override
    public boolean isSame(Loan other) {
        if (other == this) {
            return true;
        }

        return other != null
                && other.getLoanId().equals(getLoanId())
                && other.getName().equals(getName())
                && other.getNric().equals(getNric())
                && other.getBike().equals(getBike())
                && (other.getEmail().equals(getEmail()) || other.getPhone().equals(getPhone())
                || other.getLoanRate().equals(getLoanRate()) || other.getLoanStartTime().equals(getLoanStartTime()));
    }

    /**
     * Checks if this Loan is equal to the specified Loan, but only compares editable fields.
     * Editable fields refer to fields that can be edited through the "Edit Command", which are
     * the Name, NRIC, Phone, Bike, Loan rate, Email and Tags.
     *
     * @return true if the editable fields are equal.
     */
    public boolean hasEqualEditableFields(Loan other) {
        if (other == this) {
            return true;
        }

        return other.getName().equals(getName())
                && other.getNric().equals(getNric())
                && other.getPhone().equals(getPhone())
                && other.getBike().equals(getBike())
                && other.getLoanRate().equals(getLoanRate())
                && other.getEmail().equals(getEmail())
                && other.getTags().equals(getTags());
    }

    /**
     * Calculates the cost of the current Loan, provided it has already been returned.
     */
    public double calculateCost() {
        assert(endTime != null);
        assert(loanStatus == LoanStatus.RETURNED);

        // Find the time the loan was taken out for, then pass it into LoanRate to get the cost.
        long timeLoaned = this.getLoanStartTime().loanTimeDifferenceMinutes(this.getLoanEndTime());
        return this.getLoanRate().calculateCost(timeLoaned);
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
        return otherLoan.getLoanId().equals(getLoanId())
                && otherLoan.getName().equals(getName())
                && otherLoan.getNric().equals(getNric())
                && otherLoan.getPhone().equals(getPhone())
                && otherLoan.getEmail().equals(getEmail())
                && otherLoan.getLoanStatus().equals(getLoanStatus())
                && otherLoan.getBike().equals(getBike())
                && otherLoan.getLoanRate().equals(getLoanRate())
                && otherLoan.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, name, nric, phone, email, bike, rate, startTime, endTime, loanStatus, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Loan ID: ")
                .append(getLoanId())
                .append(" Name: ")
                .append(getName())
                .append(" Nric: ")
                .append(getNric())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Status: ")
                .append(getLoanStatus())
                .append(" Bike: ")
                .append(getBike())
                .append(" LoanRate: ")
                .append(getLoanRate())
                .append(" LoanStartTime: ")
                .append(getLoanStartTime())
                .append(" LoanEndTime: ")
                .append(getLoanEndTime())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
