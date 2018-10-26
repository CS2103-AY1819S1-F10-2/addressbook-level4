package loanbook.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import loanbook.model.loan.Loan;

/**
 * An UI component that displays information of a {@code Loan}.
 */
public class LoanCard extends UiPart<Region> {

    private static final String FXML = "LoanListCard.fxml";
    private static final String[] TAG_COLOR_STYLES =
        {"teal", "red", "yellow", "blue", "orange", "brown", "green", "pink", "black", "grey"};

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
    private Label nric;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label bike;
    @FXML
    private Label rate;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private FlowPane tags;

    public LoanCard(Loan loan, int displayedIndex) {
        super(FXML);
        this.loan = loan;
        id.setText(Integer.toString(displayedIndex));
        name.setText(loan.getName().value);
        nric.setText(loan.getNric().getCensored());
        phone.setText(loan.getPhone().getCensored());
        email.setText(loan.getEmail().getCensored());
        bike.setText(loan.getBike().getName().value);
        rate.setText(loan.getLoanRate().toString());
        startTime.setText(loan.getLoanStartTime().toString());
        // TODO Set the endtime to display correctly here
        endTime.setText("PLACEHOLDER");
        initTags(loan);
    }

    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        // we use the hash code of the tag name to generate a random color, so that the color remain consistent
        // between different runs of the program while still making it random enough between tags.
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }

    /**
     * Creates the tag labels for {@code person}.
     */
    private void initTags(Loan loan) {
        loan.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.value);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.value));
            tags.getChildren().add(tagLabel);
        });
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
