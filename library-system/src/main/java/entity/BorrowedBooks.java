package entity;

import java.sql.Timestamp;
import java.util.Objects;

public class BorrowedBooks {
    private Long id;
    private Integer user_id;
    private Integer book_id;
    private Timestamp borrow_date;

    public BorrowedBooks() {
    }

    public BorrowedBooks(Long id, Integer user_id, Integer book_id, Timestamp borrow_date) {
        this.id = id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.borrow_date = borrow_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Timestamp getBorrow_date() {
        return borrow_date;
    }

    public void setBorrow_date(Timestamp borrow_date) {
        this.borrow_date = borrow_date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BorrowedBooks that = (BorrowedBooks) o;
        return Objects.equals(id, that.id) && Objects.equals(user_id, that.user_id) && Objects.equals(book_id, that.book_id) && Objects.equals(borrow_date, that.borrow_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, book_id, borrow_date);
    }

    @Override
    public String toString() {
        return "BorrowedBooks{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", book_id=" + book_id +
                ", borrow_date=" + borrow_date +
                '}';
    }
}
