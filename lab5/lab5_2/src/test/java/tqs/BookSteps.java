package tqs;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookSteps {
    static final Logger log = getLogger(lookup().lookupClass());
    private Library library;
    private List<Book> books;

    /*
	create a registered type named iso8601Date to map a string pattern from the feature 
	into a custom datatype. Extracted parameters should be strings.
	 */
	@ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
	public LocalDate iso8601Date(String year, String month, String day){
		return Utils.localDateFromDateParts(year, month, day);
	}
	

	/**
	 * load a data table from the feature (tabular format) and call this method
	 * for each row in the table. Injected parameter is a map with column name --> value
	 */
	@DataTableType
	public Book bookEntry(Map<String, String> tableEntry){
		return new Book(
				tableEntry.get("title"),
				tableEntry.get("author"),
				Utils.isoTextToLocalDate( tableEntry.get("published") ) );
	}

    @Given("I have the following books in the Library")
    public void setupLibrary(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        library = new Library();    

        for (Map<String, String> row : rows) {
            Book book = bookEntry(row);
            library.addBook(book);
        }
    }

    @When("the costumer searches for books with the author {string}")
    public void searchBooksByAuthor(String author) {
        books = library.findBooksByAuthor(author);
        log.debug("Books found from author {}: {}", author, books);
    }

    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void searchBooksPublishedBetween(LocalDate from, LocalDate to) {
        books = library.findBooks(from, to);
        log.debug("Books found from {} to {}: {}", from, to, books);
    }

    @When("the costumer searches for books with the title {string}")
    public void searchBooksByTitle(String title) {
        books = library.findBooksByTitle(title);
        log.debug("Books found with title {}: {}", title, books);
    }

    @Then("{int} books should have been found")
    public void verifyAmountOfBooks(int amount, DataTable books_expected) {
        log.debug("Found {} books", books.size());
        assertTrue(books.size() == amount);

        List<Map<String, String>> rows = books_expected.asMaps(String.class, String.class);

        for (int i = 0; i < amount; i++) {
            Book book = books.get(i);
            Map<String, String> row = rows.get(i);
            Book expectedBook = bookEntry(row);
            assertEquals(expectedBook, book);
        }
    }
}
