package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.book.Book;

public interface ReadOnlyLibrary {
    ObservableList<Book> getLibraryList();

}
