package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAREGIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Link a caregiver and a patient by their nric and update person model accordingly.
 */
public class LinkCommand extends Command {

    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links a caregiver and a patient by their NRICs.\n"
            + "Parameters: "
            + PREFIX_PATIENT + "PATIENT_NRIC "
            + PREFIX_CAREGIVER + "CAREGIVER_NRIC\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATIENT + "S1234567A "
            + PREFIX_CAREGIVER + "S7654321B";

    public static final String MESSAGE_SUCCESS = "Linked %1$s and %2$s";

    public static final String MESSAGE_DUPLICATE_LINK = "This link already exists in the address book";

    public static final String PERSON_NOT_FOUND = "Incorrect NRIC. Person not found";

    private final String patientNric;
    private final String caregiverNric;

    /**
     * Creates a LinkCommand to link the specified {@code Patient} and {@code Caregiver}
     */
    public LinkCommand(String patientNric, String caregiverNric) {
        this.patientNric = patientNric;
        this.caregiverNric = caregiverNric;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person patient = model.getPerson(patientNric);
        Person caregiver = model.getPerson(caregiverNric);
        if (patient == null || caregiver == null) {
            throw new CommandException(PERSON_NOT_FOUND);
        }

        if (model.hasLink(patient, caregiver)) {
            throw new CommandException(MESSAGE_DUPLICATE_LINK);
        }

        model.addLink(patient, caregiver);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(patient), Messages.format(caregiver)));
    }

}
