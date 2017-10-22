package pimp.logic.parser;

import static java.util.Objects.requireNonNull;
import static pimp.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static pimp.logic.commands.RemarkCommand.MESSAGE_USAGE;

import pimp.commons.core.index.Index;
import pimp.commons.exceptions.IllegalValueException;
import pimp.logic.commands.RemarkCommand;
import pimp.logic.parser.exceptions.ParseException;
import pimp.model.person.Remark;

/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    @Override
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        String remark = argMultimap.getValue(CliSyntax.PREFIX_REMARK).orElse("");
        return new RemarkCommand(index, new Remark(remark));
    }
}

