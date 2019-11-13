package com.ceng319.partsCrib.Model;

public class Users {
    private String Student_Number, Password, First_Name, Last_Name, Email;

    public Users(){

    }

    public Users(String student_Number, String password, String first_Name, String last_Name, String email) {
        this.Student_Number = student_Number;
        this.Password = password;
        this.First_Name = first_Name;
        this.Last_Name = last_Name;
        this.Email = email;
    }

    public String getStudent_Number() {
        return Student_Number;
    }

    public void setStudent_Number(String student_Number) {
        this.Student_Number = student_Number;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        this.First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        this.Last_Name = last_Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }
}
