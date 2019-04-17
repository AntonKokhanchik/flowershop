package flowershop.backend.dto;

import flowershop.backend.entity.UserEntity;

import java.math.BigDecimal;

public class User {
    private String login;
    private String password;
    private String fullName;
    private String address;
    private String phone;
    private BigDecimal balance;
    private Integer discount;

    public User(String login, String password, String fullName, String address,
                String phone){
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
    }

    public User(UserEntity user){
        login = user.getLogin();
        password = user.getPassword();
        fullName = user.getFullName();
        address = user.getAddress();
        phone = user.getPhone();
        balance = user.getBalance();
        discount = user.getDiscount();
    }

    public UserEntity toEntity(){
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

    public boolean isAdmin(){return login.equals("admin");}

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
