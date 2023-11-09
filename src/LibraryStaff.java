import java.util.Objects;

public class LibraryStaff {
    private int staffId;
    private String name;
    private String role;
    private String username;
    private String password;

    // Constructors, getters, and setters
    public LibraryStaff(int staffId, String name, String role, String username, String password) {
        this.staffId = staffId;
        this.name = name;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Method to verify staff credentials
    public boolean verifyCredentials(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // Method to authorize access to add or remove books from the catalog
    public boolean canManageCatalog() {
        return Objects.equals(role, "Librarian") || Objects.equals(role, "Manager");
        // You can define specific roles that are authorized to manage the catalog.
    }

    // Methods specific to library staff
}
