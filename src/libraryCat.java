import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class libraryCat {
    private static List<Book> catalog = new ArrayList<>();
    private static List<LibraryMember> members = new ArrayList<>();
    private static final String BOOKS_FILE = "booksCatalog.txt";


    public static void main(String[] args) {
        loadBooks();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMainMenu();
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                continue;
            }

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    removeBook(scanner);
                    break;
                case 3:
                    searchBooks(scanner);
                    break;
                case 4:
                    checkoutBook(scanner);
                    break;
                case 5:
                    returnBook(scanner);
                    break;
                case 6:
                    addMember(scanner); // Add a library member
                    break;
                case 7:
                    displayMemberInfo(scanner);
                    break;
                case 8:
                    saveBooks();
                    System.out.println("Exiting the Library System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("Library Management System Menu:");
        System.out.println("1. Add new book");
        System.out.println("2. Remove book");
        System.out.println("3. Search for books");
        System.out.println("4. Check out book");
        System.out.println("5. Return book");
        System.out.println("6. Add a member");
        System.out.println("7. Display member information");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void loadBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] bookData = line.split(",");
                if (bookData.length == 5) {
                    String title = bookData[0];
                    String isbn = bookData[1];
                    String authorName = bookData[2];
                    String genre = bookData[3];
                    boolean isAvailable = Boolean.parseBoolean(bookData[4]);
                    Author author = new Author(authorName, "");
                    Book book = new Book(title, isbn, author, genre);
                    book.setAvailable(isAvailable);
                    catalog.add(book);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading books from the file: " + e.getMessage());
        }
    }

    private static void saveBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : catalog) {
                String line = String.format("%s,%s,%s,%s,%s%n",
                        book.getTitle(),
                        book.getISBN(),
                        book.getAuthor().getName(),
                        book.getGenre(),
                        book.isAvailable());
                bw.write(line);
            }
        } catch (IOException e) {
            System.err.println("Error saving books to the file: " + e.getMessage());
        }
    }


    private static void addMember(Scanner scanner) {
        System.out.println("Add a new library member:");
        System.out.print("Enter member ID: ");
        int memberId;
        try {
            memberId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid member ID format. Please enter a valid integer.");
            return;
        }

        System.out.print("Enter member name: ");
        String memberName = scanner.nextLine();

        System.out.print("Enter member address: ");
        String memberAddress = scanner.nextLine();

        System.out.print("Enter member contact number: ");
        int contactNumber;
        try {
            contactNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid contact number format. Please enter a valid integer.");
            return;
        }

        // Create the LibraryMember object
        LibraryMember newMember = new LibraryMember(memberId, memberName, memberAddress, contactNumber);

        // Add the member to the members list
        members.add(newMember);

        System.out.println("Library member added successfully!");
    }

    private static void addBook(Scanner scanner) {
        System.out.println("Add a new book to the catalog:");
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter author's name: ");
        String authorName = scanner.nextLine();

        System.out.print("Enter author's biography: ");
        String authorBio = scanner.nextLine();

        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        if (!isValidISBN(isbn)) {
            System.out.println("Invalid ISBN format. Please use a valid ISBN format.");
            return;
        }

        // Create the Author object
        Author author = new Author(authorName, authorBio);

        // Create the Book object
        Book newBook = new Book(title, isbn, author, genre);

        // Add the Book object to the catalog list
        catalog.add(newBook);


        System.out.println("Book added to the catalog successfully!");

    }

    private static boolean isValidISBN(String isbn) {
        if (isbn.matches("\\d{1,7}")) {
            return true;
        } else {
            return false;
        }
    }

    private static void removeBook(Scanner scanner) {
        System.out.println("Remove a book from the catalog:");
        System.out.print("Enter ISBN of the book to remove: ");
        String isbnToRemove = scanner.nextLine();

        // Validate ISBN format
        if (!isValidISBN(isbnToRemove)) {
            System.out.println("Invalid ISBN format. Please enter a valid ISBN.");
            return;
        }

        // Find the index of the book with the matching ISBN
        int indexToRemove = -1;
        for (int i = 0; i < catalog.size(); i++) {
            if (catalog.get(i).getISBN().equals(isbnToRemove)) {
                indexToRemove = i;
                break;
            }
        }

        if (indexToRemove != -1) {
            // Book found and removed
            catalog.remove(indexToRemove);

            System.out.println("Book with ISBN " + isbnToRemove + " has been removed from the catalog.");
        } else {
            // Book not found
            System.out.println("Book with ISBN " + isbnToRemove + " was not found in the catalog.");
        }
    }


    private static void searchBooks(Scanner scanner) {
        System.out.println("Search for books by title, author, or genre:");
        System.out.print("Enter search criteria: ");
        String searchCriteria = scanner.nextLine().toLowerCase(); // Convert to lowercase for case-insensitive search

        if (searchCriteria.isEmpty()) {
            System.out.println("Search criteria cannot be empty. Please enter a valid search criteria.");
            return;
        }

        // Iterate through the catalog to find matching books
        boolean foundMatch = false;
        for (Book book : catalog) {
            if (book.getTitle().toLowerCase().contains(searchCriteria) ||
                    book.getAuthor().getName().toLowerCase().contains(searchCriteria) ||
                    book.getGenre().toLowerCase().contains(searchCriteria)) {
                // Display matching book information
                System.out.println("Title: " + book.getTitle());
                System.out.println("ISBN: " + book.getISBN());
                System.out.println("Author: " + book.getAuthor().getName());
                System.out.println("Genre: " + book.getGenre());
                System.out.println("Availability: " + (book.isAvailable() ? "Available" : "Not Available"));
                System.out.println();
                foundMatch = true;
            }
        }

        if (!foundMatch) {
            System.out.println("No books found matching the search criteria: " + searchCriteria);
        }
    }


    private static void checkoutBook(Scanner scanner) {
        System.out.println("Checkout a book for a library member:");
        System.out.print("Enter member ID: ");
        int memberId;
        try {
            memberId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid member ID format. Please enter a valid integer.");
            return;
        }

        // Find the member by ID
        LibraryMember member = findMemberById(memberId);

        if (member == null) {
            System.out.println("Member with ID " + memberId + " not found.");
            return;
        }

        System.out.print("Enter ISBN of the book to checkout: ");
        String isbnToCheckout = scanner.nextLine();

        // Find the book by ISBN
        Book book = findBookByISBN(isbnToCheckout);

        if (book == null) {
            System.out.println("Book with ISBN " + isbnToCheckout + " not found in the catalog.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Sorry, the book with ISBN " + isbnToCheckout + " is not available for checkout.");
            return;
        }

        // Perform the checkout
        member.borrowBook(book);

        System.out.println("Book with ISBN " + isbnToCheckout + " has been checked out by " + member.getName());
    }



    private static void returnBook(Scanner scanner) {
        System.out.println("Return a book by a library member:");
        System.out.print("Enter member ID: ");
        int memberId;
        try {
            memberId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid member ID format. Please enter a valid integer.");
            return;
        }

        // Find the member by ID
        LibraryMember member = findMemberById(memberId);

        if (member == null) {
            System.out.println("Member with ID " + memberId + " not found.");
            return;
        }

        System.out.print("Enter ISBN of the book to return: ");
        String isbnToReturn = scanner.nextLine();

        // Find the book by ISBN
        Book book = findBookByISBN(isbnToReturn);

        if (book == null) {
            System.out.println("Book with ISBN " + isbnToReturn + " not found.");
            return;
        }

        // Check if the book is in the member's list of borrowed books
        boolean bookBorrowed = false;
        for (Book borrowedBook : member.getBorrowedBooks()) {
            if (borrowedBook.getISBN().equals(isbnToReturn)) {
                bookBorrowed = true;
                break;
            }
        }

        if (bookBorrowed) {
            // Perform the return
            member.returnBook(book);
            System.out.println("Book with ISBN " + isbnToReturn + " has been returned by " + member.getName());
        } else {
            System.out.println("The book with ISBN " + isbnToReturn + " is not borrowed by " + member.getName());
        }
    }


    // Helper method to find a member by ID
    private static LibraryMember findMemberById(int memberId) {
        for (LibraryMember member : members) {
            if (member.getMemberId() == memberId) {
                return member;
            }
        }
        return null;
    }

    // Helper method to find a book by ISBN
    private static Book findBookByISBN(String isbn) {
        for (Book book : catalog) {
            if (book.getISBN().equals(isbn)) {
                return book;
            }
        }
        return null;
    }


    private static void displayMemberInfo(Scanner scanner) {
        System.out.println("Display information about a library member:");
        System.out.print("Enter member ID: ");
        int memberId;
        try {
            memberId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid member ID format. Please enter a valid integer.");
            return;
        }

        // Find the member by ID
        LibraryMember member = findMemberById(memberId);

        if (member == null) {
            System.out.println("Member with ID " + memberId + " not found.");
            return;
        }

        System.out.println("Member Information:");
        System.out.println("Name: " + member.getName());
        System.out.println("Address: " + member.getAddress());
        System.out.println("Contact Information: " + member.getContactNo());

        // Display borrowed books
        List<Book> borrowedBooks = member.getBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("Borrowed Books: None");
        } else {
            System.out.println("Borrowed Books:");
            for (Book book : borrowedBooks) {
                System.out.println("  - Title: " + book.getTitle());
                System.out.println("    ISBN: " + book.getISBN());
                System.out.println("    Author: " + book.getAuthor().getName());
                System.out.println("    Genre: " + book.getGenre());
            }
        }
    }


}
