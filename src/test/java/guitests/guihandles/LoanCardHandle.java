package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import loanbook.model.loan.Loan;

/**
 * Provides a handle to a loan card in the loan list panel.
 */
public class LoanCardHandle extends ListCardHandle<Loan> {

    private static final String NAME_FIELD_ID = "#name";
    private static final String NRIC_FIELD_ID = "#nric";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String BIKE_FIELD_ID = "#bike";
    private static final String LOANRATE_FIELD_ID = "#rate";
    private static final String LOANSTARTTIME_FIELD_ID = "#startTime";
    private static final String LOANENDTIME_FIELD_ID = "#endTime";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label nameLabel;
    private final Label nricLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label bikeLabel;
    private final Label rateLabel;
    private final Label startTimeLabel;
    private final Label endTimeLabel;
    private final List<Label> tagLabels;

    public LoanCardHandle(Node cardNode) {
        super(cardNode);

        nameLabel = getChildNode(NAME_FIELD_ID);
        nricLabel = getChildNode(NRIC_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        bikeLabel = getChildNode(BIKE_FIELD_ID);
        rateLabel = getChildNode(LOANRATE_FIELD_ID);
        startTimeLabel = getChildNode(LOANSTARTTIME_FIELD_ID);
        endTimeLabel = getChildNode(LOANENDTIME_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getNric() {
        return nricLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getBike() {
        return bikeLabel.getText();
    }

    public String getLoanRate() {
        return rateLabel.getText();
    }

    public String getLoanStartTime() {
        return startTimeLabel.getText();
    }

    public String getLoanEndTime() {
        return endTimeLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public List<String> getTagStyleClasses(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag."));
    }

    /**
     * Returns true if this handle contains {@code loan}.
     */
    public boolean contains(Loan loan) {
        return getName().equals(loan.getName().value)
                && getNric().equals(loan.getNric().getCensored())
                && getPhone().equals(loan.getPhone().getCensored())
                && getEmail().equals(loan.getEmail().getCensored())
                && getBike().equals(loan.getBike().getName().value)
                && getLoanRate().equals(loan.getLoanRate().toString())
                && getLoanStartTime().equals(loan.getLoanStartTime().toString());
    }
}
