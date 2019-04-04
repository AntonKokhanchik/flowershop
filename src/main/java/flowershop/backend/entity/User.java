package flowershop.backend.entity;

import java.util.Objects;

public class User {
    private int id;
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
        this.phone = phone;
        this.balance = balance;
        this.discount = discount;
    }

    public User(User user){
        this.login = user.login;
        this.password = user.password;
        this.fullName = user.fullName;
        this.address = user.address;
        this.phone = user.phone;
        this.balance = user.balance;
        this.discount = user.discount;
    }

    public User(String login, String password, String fullName, String address,
                String phone){
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Float.compare(user.balance, balance) == 0 &&
                discount == user.discount &&
                login.equals(user.login) &&
                password.equals(user.password) &&
                Objects.equals(fullName, user.fullName) &&
                Objects.equals(address, user.address) &&
                Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, fullName, address, phone, balance, discount);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", balance=" + balance +
                ", discount=" + discount +
                '}';
    }
}
