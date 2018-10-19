package seedu.address.testutil;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasBike(Bike bike) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addBike(Bike bike) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteBike(Bike target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateBike(Bike target, Bike editedBike) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Bike> getFilteredBikeList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredBikeList(Predicate<Bike> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasLoan(Loan loan) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addLoan(Loan loan) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteLoan(Loan target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateLoan(Loan target, Loan editedLoan) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Loan> getFilteredLoanList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredLoanList(Predicate<Loan> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canUndoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void undoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void redoAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void commitAddressBook() {
        throw new AssertionError("This method should not be called.");
    }
}