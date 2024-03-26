package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyLibrary;
import seedu.address.model.book.Book;
import seedu.address.model.book.Library;

@JsonRootName(value = "library")
class JsonSerializableLibrary {

    private final List<JsonAdaptedBook> bookList = new ArrayList<>();

    @JsonCreator
    public JsonSerializableLibrary(@JsonProperty("bookList") List<JsonAdaptedBook> bookList) {
        this.bookList.addAll(bookList);
    }

    public JsonSerializableLibrary(ReadOnlyLibrary source) {
        this.bookList.addAll(source.getLibraryList().stream().map(JsonAdaptedBook::new).collect(Collectors.toList()));
    }

    public Library toModelType() throws IllegalValueException {
        Library library = new Library();
        for (JsonAdaptedBook jsonAdaptedBook : bookList) {
            Book book = jsonAdaptedBook.toModelType();
            library.addBook(book);
        }
        return library;
    }
}
