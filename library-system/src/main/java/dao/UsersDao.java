package dao;

import entity.Users;
import util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;

public class UsersDao {
    private static final UsersDao INSTANCE = new UsersDao();

    private static final String SAVE_SQL = """
            insert into users(name)
            values (?)
            """;

    public static Users save(Users users){
        try (var connection = ConnectionManager.open(); var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, users.getName());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if(keys.next()){
                users.setId(keys.getLong("id"));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static UsersDao getInstance() {
        return INSTANCE;
    }
    private UsersDao() {}
}
