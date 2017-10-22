package seedu.address.commons.events.ui;


import seedu.address.commons.events.BaseEvent;

/** Indicates the AddressBook in the model has changed*/
public class ToggleStatisticsPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
