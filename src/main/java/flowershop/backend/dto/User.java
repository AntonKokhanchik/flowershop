package flowershop.backend.dto;

import flowershop.backend.entity.UserEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class User implements Serializable {
    private String login;
    private String password;
    private String fullName;
    private String address;
    private String phone;
    private BigDecimal balance;
    private Integer discount;

    public User() { }

    public User(String login, String password, String fullName, String address,
                String phone) {
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
    }

    public User(UserEntity user) {
        login = user.getLogin();
        password = user.getPassword();
        fullName = user.getFullName();
        address = user.getAddress();
        phone = user.getPhone();
        balance = user.getBalance();
        discount = user.getDiscount();
    }

    public UserEntity toEntity() {
        UserEntity entity = new UserEntity();
        entity.setLogin(login);
        entity.setPassword(password);
        entity.setFullName(fullName);
        entity.setAddress(address);
        entity.setPhone(phone);
        entity.setBalance(balance);
        entity.setDiscount(discount);
        return entity;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public boolean isAdmin() {
        return login.equals("admin");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(fullName, user.fullName) &&
                Objects.equals(address, user.address) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(balance, user.balance) &&
                Objects.equals(discount, user.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, fullName, address, phone, balance, discount);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + "*".repeat(password.length()) + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", balance=" + balance +
                ", discount=" + discount +
                '}';
    }
}
