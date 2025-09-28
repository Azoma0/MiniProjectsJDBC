package by.azoma;

import by.azoma.dao.StudentDAO;
import by.azoma.entity.Student;

import java.util.Scanner;

public class StudentRunner {
    public static void main(String[] args) {
        StudentDAO studentDAO = StudentDAO.getInstance();
        Student student = new Student();
        Scanner scanner = new Scanner(System.in);

        boolean isComplete = false;

        while (!isComplete){
            String name;
            int age;
            Long id;
            menu();
            System.out.print("Выберите операцию: ");
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    scanner.nextLine();
                    System.out.print("Введите имя: ");
                    name = scanner.nextLine();
                    System.out.print("Введите возвраст: ");
                    age = scanner.nextInt();
                    student.setName(name);
                    student.setAge(age);
                    System.out.println(studentDAO.save(student));
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.print("Введите id: ");
                    id = scanner.nextLong();
                    studentDAO.delete(id);
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.print("Введите id: ");
                    id = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Введите имя: ");
                    name = scanner.nextLine();
                    System.out.print("Введите возвраст: ");
                    age = scanner.nextInt();
                    student.setId(id);
                    student.setName(name);
                    student.setAge(age);
                    System.out.println(studentDAO.update(student));
                    break;
                case 4:
                    System.out.println(studentDAO.show_all());
                    break;
                case 0:
                    isComplete = true;
            }
        }
    }

    public static void menu(){
        System.out.println("1-добавить");
        System.out.println("2-удалить");
        System.out.println("3-обновить");
        System.out.println("4-вывести всех");
        System.out.println("0-выйти");
    }
}