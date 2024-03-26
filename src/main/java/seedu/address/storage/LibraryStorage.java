package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyLibrary;

public interface LibraryStorage {
    Path getLibraryFilePath();

    Optional<ReadOnlyLibrary> readLibrary() throws DataLoadingException;

    Optional<ReadOnlyLibrary> readLibrary(Path filePath) throws DataLoadingException;

    void saveLibrary(ReadOnlyLibrary library) throws IOException;

    void saveLibrary(ReadOnlyLibrary library, Path filePath) throws IOException;
}
