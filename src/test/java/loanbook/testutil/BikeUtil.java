package loanbook.testutil;

import static loanbook.logic.parser.CliSyntax.PREFIX_NAME;

import loanbook.logic.commands.AddBikeCommand;
import loanbook.model.bike.Bike;

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
