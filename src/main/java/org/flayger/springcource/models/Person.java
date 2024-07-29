package org.flayger.springcource.models;


import javax.validation.constraints.*;

public class Person {
    private int id;

    @NotEmpty(message="must not be empty")
    @Size(min =2, max=30, message = "wrong name, between 2 or 30 characters")
    private String name;

    @Min(value = 0, message = "age should not be negative")
    private int age;

    @NotEmpty(message = "email should not be empty")
    @Email(message = "email should be valid")
    private String email;

    // regexp = "Country, Town, 6 numbers (postal code)
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}",
            message = "Your address should be in this format : Country, Town, 6 numbers (postal code)")
    private String address;

    public Person(int id, String name, int age, String email, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
