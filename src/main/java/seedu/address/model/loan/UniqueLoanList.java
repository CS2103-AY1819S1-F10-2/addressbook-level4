package seedu.address.model.loan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.loan.exceptions.DuplicateLoanException;
import seedu.address.model.loan.exceptions.LoanNotFoundException;

/**
 * A list of loans that enforces uniqueness between its elements and does not allow nulls.
 * A loan is considered unique by comparing using {@code Loan#isSameLoan(Loan)}. As such, adding and updating of
 * loans uses Loan#isSameLoan(Loan) for equality so as to ensure that the loan being added or updated is
 * unique in terms of identity in the UniqueLoanList. However, the removal of a loan uses Loan#equals(Object) so
 * as to ensure that the loan with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Loan#isSameLoan(Loan)
 */
public class UniqueLoanList implements Iterable<Loan> {

    private final ObservableList<Loan> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent loan as the given argument.
     */
    public boolean contains(Loan toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameLoan);
    }

    /**
     * Adds a loan to the list.
     * The loan must not already exist in the list.
     */
    public void add(Loan toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateLoanException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the loan {@code target} in the list with {@code editedLoan}.
     * {@code target} must exist in the list.
     * The loan identity of {@code editedLoan} must not be the same as another existing loan in the list.
     */
    public void setLoan(Loan target, Loan editedLoan) {
        requireAllNonNull(target, editedLoan);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new LoanNotFoundException();
        }

        if (!target.isSameLoan(editedLoan) && contains(editedLoan)) {
            throw new DuplicateLoanException();
        }

        internalList.set(index, editedLoan);
    }

    /**
     * Removes the equivalent loan from the list.
     * The loan must exist in the list.
     */
    public void remove(Loan toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new LoanNotFoundException();
        }
    }

    public void setLoans(UniqueLoanList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code loans}.
     * {@code loans} must not contain duplicate loans.
     */
    public void setLoans(List<Loan> loans) {
        requireAllNonNull(loans);
        if (!loansAreUnique(loans)) {
            throw new DuplicateLoanException();
        }

        internalList.setAll(loans);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Loan> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Loan> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLoanList // instanceof handles nulls
                        && internalList.equals(((UniqueLoanList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code loans} contains only unique loans.
     */
    private boolean loansAreUnique(List<Loan> loans) {
        for (int i = 0; i < loans.size() - 1; i++) {
            for (int j = i + 1; j < loans.size(); j++) {
                if (loans.get(i).isSameLoan(loans.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
