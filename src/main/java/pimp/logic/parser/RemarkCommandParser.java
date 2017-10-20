package pimp.logic.parser;

import static java.util.Objects.requireNonNull;

import pimp.commons.core.Messages;
import pimp.commons.core.index.Index;
import pimp.logic.commands.RemarkCommand;
import pimp.logic.parser.exceptions.ParseException;
import pimp.commons.exceptions.IllegalValueException;
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
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String remark = argMultimap.getValue(CliSyntax.PREFIX_REMARK).orElse("");
        return new RemarkCommand(index, new Remark(remark));
    }
}

