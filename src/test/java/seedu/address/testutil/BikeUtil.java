package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.AddBikeCommand;
import seedu.address.model.bike.Bike;

/**
 * A utility class for Bike.
 */
public class BikeUtil {

    /**
     * Returns an add command string for adding the {@code bike}.
     */
    public static String getAddBikeCommand(Bike bike) {
        return AddBikeCommand.COMMAND_WORD + " " + getBikeDetails(bike);
    }

    /**
     * Returns the part of command string for the given {@code bike}'s details.
     */
    public static String getBikeDetails(Bike bike) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + bike.getName().value + " ");
        return sb.toString();
    }
}
