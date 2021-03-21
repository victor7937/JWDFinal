package by.victor.jwd.bean;

import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable {

    private static final long serialVersionUID = -1018632074131042436L;

    private String name;
    private String email;
    private String password;
    private String phone;
    private String country;
    private String city;
    private String address;


    public Customer(){ }

    public Customer(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Customer(String name, String email, String password, String phone, String country, String city, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
