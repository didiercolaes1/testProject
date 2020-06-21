package edu.ap.spring.model;

public class Student {
    String voornaam;
    String Achternaam;
    String telefoon;

    public Student(String voornaam, String achternaam, String telefoon) {
        this.voornaam = voornaam;
        Achternaam = achternaam;
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
        return Achternaam;
    }

    public void setAchternaam(String achternaam) {
        Achternaam = achternaam;
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
                ", Achternaam='" + Achternaam + '\'' +
                ", telefoon='" + telefoon + '\'' +
                '}';
    }
}
