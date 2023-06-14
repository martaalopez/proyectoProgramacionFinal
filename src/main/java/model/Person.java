package model;


public abstract class Person {
    protected int id_person;
    protected String name;
    protected String mail;
    protected int phoneNumber;

    protected String password;
    protected String username;
    protected String type;


    public Person(int  id_person, String name, String mail, int  phoneNumber,String password,String username,String type) {
        this.id_person = id_person;
        this.name=name;
        this.mail=mail;
        this.phoneNumber=phoneNumber;
        this.password=password;
        this.username=username;
        this.type=type;

    }
    public Person() {
        this(0,"","",0,"","","");
    }

    public Person(String name, int phoneNumber, String mail, String username, String password, String type) {
    }

    public Person(int idPerson, String name, String mail, int phoneNumber) {
    }

    public int getId_person() {
        return id_person;
    }

    public void setId_person(int id_person) {
        this.id_person = id_person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public abstract void imprimirInformacion();


}
