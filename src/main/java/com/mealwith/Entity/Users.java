package com.mealwith.Entity;

public class Users {
    private int id;
    private String email;
    private String role;
    private String password;
    private String firstname;
    private String lastname;
    private String city;
    private String address;
    private int zipcode;

    public Users() {
    }

    public Users(int id, String email, String role, String password, String firstname, String lastname, String city, String address, int zipcode) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
    }

    public Users(String email, String role, String password, String firstname, String lastname, String city, String address, int zipcode) {
        this.email = email;
        this.role = role;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }
}
