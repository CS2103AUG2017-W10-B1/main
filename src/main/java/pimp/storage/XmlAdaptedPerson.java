package pimp.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import pimp.model.person.Email;
import pimp.model.person.Name;
import pimp.model.person.Person;
import pimp.model.person.ReadOnlyPerson;
import pimp.commons.exceptions.IllegalValueException;
import pimp.model.person.Address;
import pimp.model.person.Phone;
import pimp.model.person.Remark;
import pimp.model.tag.Tag;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String remark;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private Date createdAt;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyPerson source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        remark = source.getRemark().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        createdAt = source.getCreatedAt();
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final Email email = new Email(this.email);
        final Address address = new Address(this.address);
        final Remark remark = new Remark(this.remark);
        final Set<Tag> tags = new HashSet<>(personTags);
        final Date createdAt;

        if (this.createdAt == null) {
            // In the event that there is no createdAt attribute for that person
            createdAt = new Date();
        } else {
            createdAt = new Date(this.createdAt.getTime());
        }

        return new Person(name, phone, email, address, remark, tags, createdAt);
    }
}
