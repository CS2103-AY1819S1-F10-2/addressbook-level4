package loanbook.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import loanbook.model.bike.Bike;

/**
 * An UI component that displays information of a {@code Bike}.
 */
public class BikeCard extends ListCard<Bike> {

    private static final String FXML = "BikeCard.fxml";

    @FXML
    private Label name;

    public BikeCard(Bike bike, int displayedIndex) {
        super(FXML, bike, displayedIndex);
        name.setText(bike.getName().value);
    }
}
