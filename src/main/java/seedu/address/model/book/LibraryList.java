package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class LibraryList implements Iterable<Book> {
    private final ObservableList<Book> internalList = FXCollections.observableArrayList();

    private final ObservableList<Book> internalUnmodifiedList =
            FXCollections.unmodifiableObservableList(internalList);

    public boolean contains(Book toCheck) {
        requireNonNull();
        return internalList.stream().anyMatch(toCheck::is);
    }
}
