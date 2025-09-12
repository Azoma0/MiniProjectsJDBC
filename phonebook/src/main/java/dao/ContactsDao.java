package dao;

import entity.Contacts;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class ContactsDao {
    private static final ContactsDao INSTANCE = new ContactsDao();

    private final String SAVE_SQL = """
            insert into contacts(name, phone, email)
            values(?,?,?) 
            """;
    private final String DELETE_SQL = """
            delete from contacts
            where id = ?
            """;
    private final String UPDATE_SQL = """
            update contacts
            set name = ?,
                phone = ?,
                email = ?
            where id = ?
            """;
    private final String FIND_BY_NAME_SQL = """
            select id, name, phone, email from contacts
            where name = ?
            """;

    public Contacts save (Contacts contacts){
        try (var connection = ConnectionManager.open(); var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, contacts.getName());
            statement.setString(2, contacts.getPhone());
            statement.setString(3, contacts.getEmail());

            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if(keys.next()){
                contacts.setId(keys.getLong("id"));
            }
            return contacts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(Long id){
        try(var conncection = ConnectionManager.open(); var statement = conncection.prepareStatement(DELETE_SQL)){
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Contacts contacts){
        try(var connection = ConnectionManager.open(); var statement = connection.prepareStatement(UPDATE_SQL)){
            statement.setString(1, contacts.getName());
            statement.setString(2, contacts.getPhone());
            statement.setString(3, contacts.getEmail());
            statement.setLong(4, contacts.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Contacts> findByName(String name){
        try(var connection = ConnectionManager.open(); var statement = connection.prepareStatement(FIND_BY_NAME_SQL)){
            statement.setString(1, name);
            var result = statement.executeQuery();
            Contacts contacts = null;
            if(result.next()){
                contacts = new Contacts(
                        result.getLong("id"),
                        result.getString("name"),
                        result.getString("phone"),
                        result.getString("email")
                );
            }

            return Optional.ofNullable(contacts);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  static ContactsDao getInstance(){
        return INSTANCE;
    }

    private ContactsDao(){

    }
}
