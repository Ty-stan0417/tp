package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyLibrary;

public class JsonLibraryStorage implements LibraryStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonLibraryStorage.class);

    private Path filePath;

    public JsonLibraryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getLibraryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyLibrary> readLibrary() throws DataLoadingException {
        return readLibrary(filePath);
    }

    public Optional<ReadOnlyLibrary> readLibrary(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableLibrary> jsonLibrary = JsonUtil.readJsonFile(
                filePath, JsonSerializableLibrary.class);
        if (!jsonLibrary.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonLibrary.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    public void saveLibrary(ReadOnlyLibrary library) throws IOException {
        saveLibrary(library, filePath);
    }

    public void saveLibrary(ReadOnlyLibrary library, Path filePath) throws IOException {
        requireNonNull(library);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableLibrary(library), filePath);
    }
}
