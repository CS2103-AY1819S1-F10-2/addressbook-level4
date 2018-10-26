package seedu.address.testutil;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.Password;
import seedu.address.model.ReadOnlyLoanBook;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void resetData(ReadOnlyLoanBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyLoanBook getLoanBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasBike(Bike bike) {
        throw new AssertionError(
            "This method should not be called.");
    }

    @Override
    public Optional<Bike> getBike(String bikeName) {
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
    public void setBikes(List<Bike> bikes) {
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
    public void setLoans(List<Loan> loans) {
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
    public boolean canUndoLoanBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedoLoanBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void undoLoanBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void redoLoanBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void commitLoanBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public String getPass() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPass(Password pass) {
        throw new AssertionError("This method should not be called.");
    }
}
