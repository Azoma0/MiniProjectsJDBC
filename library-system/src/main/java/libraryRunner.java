import dao.BooksDao;
import entity.Books;

public class libraryRunner {
    public static void main(String[] args) {
        var booksDao = BooksDao.getInstance();
        Books books = new Books();
        books.setTitle("Awfaw");
        books.setAuthor("Awfaw");
        System.out.println(booksDao.getAvailableBooks());
    }
}
