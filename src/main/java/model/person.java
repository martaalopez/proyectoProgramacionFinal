package model;

import javafx.beans.property.*;

public abstract class person {
    protected IntegerProperty id_person=new SimpleIntegerProperty();
    protected StringProperty name=new SimpleStringProperty();
    protected StringProperty mail=new SimpleStringProperty();
    protected IntegerProperty phoneNumber=new SimpleIntegerProperty();

    public person(IntegerProperty id_person,StringProperty name,StringProperty mail,IntegerProperty phoneNumber) {
        this.id_person = id_person;
        this.name=name;
        this.mail=mail;
        this.phoneNumber=phoneNumber;
    }
    public person() {
        this(new SimpleIntegerProperty(0), new SimpleStringProperty(""), new SimpleStringProperty(""),new SimpleIntegerProperty() );
    }

    public int getId_person() {
        return id_person.get();
    }

    public IntegerProperty id_personProperty() {
        return id_person;
    }

    public void setId_person(int id_person) {
        this.id_person.set(id_person);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getMail() {
        return mail.get();
    }

    public StringProperty mailProperty() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }

    public int getPhoneNumber() {
        return phoneNumber.get();
    }

    public IntegerProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public abstract void imprimirInformacion();


}


