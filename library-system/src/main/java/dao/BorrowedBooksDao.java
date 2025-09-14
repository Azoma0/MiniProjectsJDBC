package dao;

import entity.Books;
import util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowedBooksDao {
    private static final BorrowedBooksDao INSTANCE = new BorrowedBooksDao();

    private static final String CHECK_BOOK_SQL = """
            select available from books where id = ?
            """;
    private static final String UPDATE_BOOK_TO_SQL = """
            update books set available = false where id = ?
            """;
    private static final String SAVE_BOOK_SQL = """
            insert into borrowed_books(user_id, books_id) 
            values (?,?)
            """;
    private static final String DELETE_BORROW_SQL = """
            delete from borrowed_books where user_id = ? and books_id = ?
            """;
    private static final String UPDATE_BOOK__AFTER_SQL = """
            update books set available = true where id = ?
            """;
    private static final String FIND_BY_NAME_SQL = """
            select b.id, b.title, b.author, b.available from borrowed_books bb
            join books b on bb.books_id = b.id
            where bb.user_id = ?
            """;

    public  void saveBorrowBook(int userId, int booksId){
        try (var connection = ConnectionManager.open()){
            connection.setAutoCommit(false);

            try (var statemant = connection.prepareStatement(CHECK_BOOK_SQL)){
                statemant.setInt(1, booksId);
                var result = statemant.executeQuery();
                if(result.next() && !result.getBoolean("available")){
                    System.out.println("Книга уже занята");
                    return;
                }
            }

            try (var statement = connection.prepareStatement(UPDATE_BOOK_TO_SQL)){
                statement.setInt(1, booksId);
                statement.executeUpdate();
            }

            try (var statement = connection.prepareStatement(SAVE_BOOK_SQL)){
                statement.setInt(1, userId);
                statement.setInt(2, booksId);
                statement.executeUpdate();
            }

            connection.commit();
            System.out.println("Книга успешна выдана!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  void deleteBorrowBook(int userId, int booksId){
        try (var connection = ConnectionManager.open()){
            connection.setAutoCommit(false);

            try (var statemant = connection.prepareStatement(DELETE_BORROW_SQL)){
                statemant.setInt(1, userId);
                statemant.setInt(2, booksId);
                statemant.executeUpdate();
            }

            try (var statement = connection.prepareStatement(UPDATE_BOOK__AFTER_SQL)){
                statement.setInt(1, booksId);
                statement.executeUpdate();
            }

            connection.commit();
            System.out.println("Книга успешна возвращена!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  List<Books> findByName(int userId){
        try (var connection = ConnectionManager.open(); var statement = connection.prepareStatement(FIND_BY_NAME_SQL)){
            List<Books> books = new ArrayList<>();
            statement.setInt(1, userId);
            var result = statement.executeQuery();
            while (result.next()){
                Books book = new Books();
                book.setId(result.getLong("id"));
                book.setTitle(result.getString("title"));
                book.setTitle(result.getString("author"));
                book.setAvailable(result.getBoolean("available"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static BorrowedBooksDao getInstance(){
        return INSTANCE;
    }

    private BorrowedBooksDao(){}
}
