package flowershop.frontend.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

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

        return new EqualsBuilder()
                .append(login, user.login)
                .append(password, user.password)
                .append(fullName, user.fullName)
                .append(address, user.address)
                .append(phone, user.phone)
                .append(balance, user.balance)
                .append(discount, user.discount)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(login)
                .append(password)
                .append(fullName)
                .append(address)
                .append(phone)
                .append(balance)
                .append(discount)
                .toHashCode();
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
