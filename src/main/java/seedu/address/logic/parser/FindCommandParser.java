package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRAIT;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cat.CatContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Logger logger = LogsCenter.getLogger(FindCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        logger.info("Parsing FindCommand with arguments: " + args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LOCATION, PREFIX_TRAIT, PREFIX_HEALTH);

        // Validation and keyword extraction
        if (!argMultimap.getPreamble().isEmpty()) {
            // Reject any input with preamble (non-prefix text)
            logger.warning("Parsing failed: Preamble found without prefixes in input: " + args);
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (isAnyPrefixPresent(argMultimap)) {
            // Prefixes present - parse them
            List<String> nameKeywords = getKeywordsFromPrefix(argMultimap, PREFIX_NAME);
            List<String> locationKeywords = getKeywordsFromPrefix(argMultimap, PREFIX_LOCATION);
            List<String> traitKeywords = getKeywordsFromPrefix(argMultimap, PREFIX_TRAIT);
            List<String> healthKeywords = getKeywordsFromPrefix(argMultimap, PREFIX_HEALTH);

            logger.fine(String.format("Parsed keywords - Names: %d, Locations: %d, Traits: %d, Health: %d",
                    nameKeywords.size(), locationKeywords.size(), traitKeywords.size(), healthKeywords.size()));

            return new FindCommand(new CatContainsKeywordsPredicate(
                    nameKeywords, locationKeywords, traitKeywords, healthKeywords));
        } else {
            // No prefixes and no preamble - invalid (empty input or whitespace only)
            logger.warning("Parsing failed: No prefixes in input: " + args);
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if at least one of the prefixes contains a value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isAnyPrefixPresent(ArgumentMultimap argumentMultimap) {
        return Stream.of(PREFIX_NAME, PREFIX_LOCATION, PREFIX_TRAIT, PREFIX_HEALTH)
                .anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Extracts keywords from the multimap for a specific prefix.
     * Splits values by whitespace to allow multiple keywords per flag (e.g., t/friendly calico).
     */
    private List<String> getKeywordsFromPrefix(ArgumentMultimap argMultimap, Prefix prefix) {
        return argMultimap.getAllValues(prefix).stream()
                .flatMap(val -> Arrays.stream(val.split("\\s+")))
                .filter(val -> !val.isEmpty())
                .toList();
    }
}
