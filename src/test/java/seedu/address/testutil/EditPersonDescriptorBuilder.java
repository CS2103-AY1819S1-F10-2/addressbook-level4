package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditLoanDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Loan;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditLoanDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditLoanDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditLoanDescriptor();
    }

    public EditPersonDescriptorBuilder(EditLoanDescriptor descriptor) {
        this.descriptor = new EditLoanDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditLoanDescriptor} with fields containing {@code loan}'s details
     */
    public EditPersonDescriptorBuilder(Loan loan) {
        descriptor = new EditLoanDescriptor();
        descriptor.setName(loan.getName());
        descriptor.setPhone(loan.getPhone());
        descriptor.setEmail(loan.getEmail());
        descriptor.setAddress(loan.getAddress());
        descriptor.setTags(loan.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditLoanDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditLoanDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditLoanDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditLoanDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditLoanDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditLoanDescriptor build() {
        return descriptor;
    }
}
