package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.loan.Loan;

/**
 * An UI component that displays information of a {@code Loan}.
 */
public class LoanCard extends UiPart<Region> {

    private static final String FXML = "LoanListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Loan loan;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public LoanCard(Loan loan, int displayedIndex) {
        super(FXML);
        this.loan = loan;
        id.setText(displayedIndex + ". ");
        name.setText(loan.getName().value);
        phone.setText(loan.getPhone().value);
        address.setText(loan.getAddress().value);
        email.setText(loan.getEmail().value);
        loan.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.value)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LoanCard)) {
            return false;
        }

        // state check
        LoanCard card = (LoanCard) other;
        return id.getText().equals(card.id.getText())
                && loan.equals(card.loan);
    }
}
