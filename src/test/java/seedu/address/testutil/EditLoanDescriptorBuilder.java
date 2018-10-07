package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditLoanDescriptor;
import seedu.address.model.loan.Address;
import seedu.address.model.loan.Email;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.Name;
import seedu.address.model.loan.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditLoanDescriptor objects.
 */
public class EditLoanDescriptorBuilder {

    private EditLoanDescriptor descriptor;

    public EditLoanDescriptorBuilder() {
        descriptor = new EditLoanDescriptor();
    }

    public EditLoanDescriptorBuilder(EditCommand.EditLoanDescriptor descriptor) {
        this.descriptor = new EditCommand.EditLoanDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditLoanDescriptor} with fields containing {@code loan}'s details
     */
    public EditLoanDescriptorBuilder(Loan loan) {
        descriptor = new EditCommand.EditLoanDescriptor();
        descriptor.setName(loan.getName());
        descriptor.setPhone(loan.getPhone());
        descriptor.setEmail(loan.getEmail());
        descriptor.setAddress(loan.getAddress());
        descriptor.setTags(loan.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditLoanDescriptor} that we are building.
     */
    public EditLoanDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditLoanDescriptor} that we are building.
     */
    public EditLoanDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditLoanDescriptor} that we are building.
     */
    public EditLoanDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditLoanDescriptor} that we are building.
     */
    public EditLoanDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditLoanDescriptor}
     * that we are building.
     */
    public EditLoanDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditLoanDescriptor build() {
        return descriptor;
    }
}
