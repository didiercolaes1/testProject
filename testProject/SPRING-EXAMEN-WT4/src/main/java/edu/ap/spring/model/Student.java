package edu.ap.spring.model;

public class Student {
    String voornaam;
    String achternaam;
    String telefoon;

    public Student(String voornaam, String achternaam, String telefoon) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.telefoon = telefoon;
    }

    public Student() {
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getTelefoon() {
        return telefoon;
    }

    public void setTelefoon(String telefoon) {
        this.telefoon = telefoon;
    }

    @Override
    public String toString() {
        return "Student{" +
                "voornaam='" + voornaam + '\'' +
                ", Achternaam='" + achternaam + '\'' +
                ", telefoon='" + telefoon + '\'' +
                '}';
    }
}
