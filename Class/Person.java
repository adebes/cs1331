public class Person {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public static void main(String[] args) {
        Person[] duds = {new Person("Dagvid", 5), new Person("Vincent", 19),
                        new Person("Ricky", 50), new Person("Baby", 0)};
        for (Person p : duds) {
            System.out.println(p);
            System.out.println(p.getAge());
        }
    }
}
