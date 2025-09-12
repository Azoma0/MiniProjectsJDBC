import dao.ContactsDao;
import entity.Contacts;
import util.ConnectionManager;

import java.sql.SQLException;
import java.util.Scanner;

public class phoneBookRunner {
    public static void main(String[] args) {
       var contactsDao = ContactsDao.getInstance();
        Scanner scanner = new Scanner(System.in);
        Contacts contacts = new Contacts();
        boolean isTrue = false;
        String name;
        String phone;
        String email;
        Long id;
        while (!isTrue){
            System.out.println("1-добавить\n2-обновить\n3-удалить\n4-найти\n0-выход");
            System.out.print("Выберите операцию: ");
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    scanner.nextLine();
                    System.out.print("Введите имя: ");
                    name = scanner.nextLine();
                    System.out.print("Введите номер: ");
                    phone = scanner.nextLine();
                    System.out.print("Введите email: ");
                    email = scanner.nextLine();

                    contacts.setName(name);
                    contacts.setPhone(phone);
                    contacts.setEmail(email);

                    System.out.println(contactsDao.save(contacts));
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.print("Введите id: ");
                    id = scanner.nextLong();
                    System.out.print("Введите имя: ");
                    name = scanner.nextLine();
                    System.out.print("Введите номер: ");
                    phone = scanner.nextLine();
                    System.out.print("Введите email: ");
                    email = scanner.nextLine();

                    contacts.setId(id);
                    contacts.setName(name);
                    contacts.setPhone(phone);
                    contacts.setEmail(email);
                    System.out.println(contactsDao.update(contacts));
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.print("Введите id: ");
                    id = scanner.nextLong();
                    contacts.setId(id);
                    System.out.println(contactsDao.delete(id));
                    break;
                case 4:
                    scanner.nextLine();
                    System.out.print("Введите имя: ");
                    name = scanner.nextLine();
                    contacts.setName(name);
                    System.out.println(contactsDao.findByName(name));
                    break;
                case 0:
                    isTrue = true;
            }
        }
    }
}
