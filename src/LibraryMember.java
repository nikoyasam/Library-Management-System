import java.util.ArrayList;
import java.util.List;

class LibraryMember {
    private int memberId;
    private String name;
    private String address;
    private int ContactNo;
    private ArrayList<Book> borrowedBooks;


    public LibraryMember(int memberId, String name, String address, int ContactNo) {
        this.memberId = memberId;
        this.name = name;
        this.address = address;
        this.ContactNo = ContactNo;
        this.borrowedBooks = new ArrayList<>();

    }

    // Getters and setters for memberId and name

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getContactNo() {
        return ContactNo;
    }

    public void setContactNo(int contactNo) {
        ContactNo = contactNo;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }


    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            borrowedBooks.add(book);
            book.setAvailable(false);
        } else {
            System.out.println("Sorry, the book is not available for borrowing.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            book.setAvailable(true);
        } else {
            System.out.println("You did not borrow this book from the library.");
        }
    }

    // Methods for library staff to manage member accounts

    public void updateMemberInfo(String name, String address, int contactNo) {
        this.name = name;
        this.address = address;
        this.ContactNo = contactNo;
    }

    public void deleteMemberAccount() {
        borrowedBooks.clear(); // Return all borrowed books before deleting the account
        // You can implement additional logic for deleting the account from the system.
    }


}