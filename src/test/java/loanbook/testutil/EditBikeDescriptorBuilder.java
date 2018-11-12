package loanbook.testutil;

import loanbook.logic.commands.EditBikeCommand;
import loanbook.logic.commands.EditBikeCommand.EditBikeDescriptor;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Name;

/**
 * A utility class to help with building EditBikeDescriptor objects.
 */
public class EditBikeDescriptorBuilder {

    private EditBikeDescriptor descriptor;

    public EditBikeDescriptorBuilder() {
        descriptor = new EditBikeDescriptor();
    }

    public EditBikeDescriptorBuilder(EditBikeCommand.EditBikeDescriptor descriptor) {
        this.descriptor = new EditBikeCommand.EditBikeDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBikeDescriptor} with fields containing {@code bike}'s details
     */
    public EditBikeDescriptorBuilder(Bike bike) {
        descriptor = new EditBikeCommand.EditBikeDescriptor();
        descriptor.setName(bike.getName());
    }

    /**
     * Sets the {@code Name} of the {@code EditBikeDescriptor} that we are building.
     */
    public EditBikeDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    public EditBikeCommand.EditBikeDescriptor build() {
        return descriptor;
    }
}
