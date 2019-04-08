package flowershop.backend.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="USERS")
@NamedQuery(name="getAll", query="Select c from USERS c")
public class UserEntity {
    @Id
    private String login;
    @Column
    private String password;
    @Column
    private String fullName;
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private BigDecimal balance;
    @Column
    private Integer discount;

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
