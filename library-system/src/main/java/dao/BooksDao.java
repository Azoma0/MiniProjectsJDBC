package dao;

import entity.Books;
import util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BooksDao {
    private final static BooksDao INSTANCE = new BooksDao();

    private static final String SAVE_SQL = """
            insert into books(title, author) 
            values (?,?)
            """;

    private static final String GET_AVAILABLE_BOOKS_SQL = """
            select id, title, author, available from books
            where available = true
            """;
    public Books save(Books books){
        try (var connection = ConnectionManager.open(); var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, books.getTitle());
            statement.setString(2, books.getAuthor());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if(keys.next()){
                books.setId(keys.getLong("id"));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Books> getAvailableBooks(){
        try (var connection = ConnectionManager.open(); var statement = connection.prepareStatement(GET_AVAILABLE_BOOKS_SQL)){
            var result = statement.executeQuery();
            List<Books> books = new ArrayList<>();
            while (result.next()){
                books.add(new  Books(
                        result.getLong("id"),
                        result.getString("title"),
                        result.getString("author"),
                        result.getBoolean("available")
                ));

            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static BooksDao getInstance(){
        return INSTANCE;
    }
    private BooksDao(){

    }
}
