package by.azoma.dao;

import by.azoma.entity.Student;
import by.azoma.util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final StudentDAO INSTANCE = new StudentDAO();

    private static final String SAVE_SQL = """
            insert into students(name, age)
            values (?, ?);
            """;

    private static final String DELETE_SQL = """
            delete from students where id = ?;
            """;

    private static final String UPDATE_SQL = """
            update students
            set name = ?, age = ?
            where id = ?;
            """;

    private static final String SHOW_ALL_SQL = """
            select id, name, age from students;
            """;

    public Student save(Student student){
        try (var connecction = ConnectionManager.open(); var statement = connecction.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());

            statement.executeUpdate();
            var result = statement.getGeneratedKeys();
            if(result.next()){
                student.setId(result.getLong("id"));
            }
            return student;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(Long id){
        try (var connection = ConnectionManager.open(); var statement = connection.prepareStatement(DELETE_SQL)){
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Student student){
        try (var connection = ConnectionManager.open(); var statement = connection.prepareStatement(UPDATE_SQL)){
            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setLong(3, student.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> show_all(){
        try (var connection = ConnectionManager.open(); var statement = connection.prepareStatement(SHOW_ALL_SQL)){
            var result = statement.executeQuery();
            List<Student> student = new ArrayList<>();
            while (result.next()){
                student.add( new Student(
                        result.getLong("id"),
                        result.getString("name"),
                        result.getInt("age")
                ));
            }
            return student;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static StudentDAO getInstance(){
        return INSTANCE;
    }

    private StudentDAO(){

    }
}
