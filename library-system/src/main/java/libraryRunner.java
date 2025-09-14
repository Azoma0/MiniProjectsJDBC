import dao.BooksDao;
import dao.BorrowedBooksDao;
import dao.UsersDao;
import entity.Books;
import entity.Users;

import java.awt.print.Book;
import java.util.List;
import java.util.Scanner;

public class libraryRunner {
    public static void main(String[] args) {
        var booksDao = BooksDao.getInstance();
        var usersDao = UsersDao.getInstance();
        var borrowedBooksDao = BorrowedBooksDao.getInstance();
        Scanner scanner = new Scanner(System.in);
        boolean isTrue = false;

        int userId;
        int bookId;
        while (!isTrue){
            System.out.println("\n=== Library Menu ===");
            System.out.println("1. Добавление книги");
            System.out.println("2. Регистрация пользователя");
            System.out.println("3. Показать доступные книги");
            System.out.println("4. Выдать книгу пользователю");
            System.out.println("5. Вернуть книгу");
            System.out.println("6. Показать книги пользователя");
            System.out.println("0. Выход");

            System.out.print("Выберите операцию: ");
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    scanner.nextLine();
                    System.out.println("\nВведите название: ");
                    String title = scanner.nextLine();
                    System.out.println("\n📖 Введите автора: ");
                    String author = scanner.nextLine();
                    Books books = new Books();
                    books.setTitle(title);
                    books.setAuthor(author);
                    booksDao.save(books);
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.println("\nВведите имя: ");
                    String name = scanner.nextLine();
                    Users users = new Users();
                    users.setName(name);
                    UsersDao.save(users);
                    break;
                case 3:
                    System.out.println("\n📖 Доступные книги:");
                    List<Books> availableBooks = booksDao.getAvailableBooks();
                    for (Books b : availableBooks) {
                        System.out.println(b.getId() + ". " + b.getTitle() + " — " + b.getAuthor());
                    }
                    break;
                case 4:
                    scanner.nextLine();
                    System.out.print("Введите ID пользователя: ");
                    userId = scanner.nextInt();
                    System.out.print("Введите ID книги: ");
                    bookId = scanner.nextInt();
                    borrowedBooksDao.saveBorrowBook(userId, bookId);
                    break;
                case 5:
                    scanner.nextLine();
                    System.out.print("Введите ID пользователя: ");
                    userId = scanner.nextInt();
                    System.out.print("Введите ID книги: ");
                    bookId = scanner.nextInt();
                    borrowedBooksDao.deleteBorrowBook(userId, bookId);
                    break;
                case 6:
                    scanner.nextLine();
                    System.out.print("Введите ID пользователя: ");
                    userId = scanner.nextInt();
                    List<Books> userBooks = borrowedBooksDao.findByName(userId);
                    System.out.println("\n📚 Книги у пользователя #" + userId + ":");
                    for (Books b : userBooks) {
                        System.out.println(b.getId() + ". " + b.getTitle() + " — " + b.getAuthor());
                    }
                    break;
                case 0:
                    isTrue = true;
            }
        }
    }
}
