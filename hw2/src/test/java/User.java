public class User {
    private String name;
    private int age;
    private String city;
    private String occupation;
    private int salary;

    public User(String name, int age, String city, String occupation, int salary) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.occupation = occupation;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}