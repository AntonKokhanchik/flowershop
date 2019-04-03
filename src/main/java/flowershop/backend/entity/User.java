package flowershop.backend.entity;

public class User {
    public static final User ADMIN = new User("admin", "admin123");

    private String login;
    private String password;
    private String fullName;
    private String address;
    private String phone;
    private float balance;
    private int discount;

    private User(String login, String password){
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, String fullName, String address ,
                String phone, float balance, int discount){
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.balance = balance;
        this.discount = discount;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
