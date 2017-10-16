public abstract class Animal {
     private String name;

     public Animal(String name, Type type) {
        this.name = name;
     }

     public abstract String noise();
}
