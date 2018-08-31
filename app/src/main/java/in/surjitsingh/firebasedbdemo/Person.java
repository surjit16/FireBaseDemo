package in.surjitsingh.firebasedbdemo;

public class Person {
    String name, id, email;

    public Person(String name, String id, String email) {
        this.name = name;
        this.id = id;
        this.email = email;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
