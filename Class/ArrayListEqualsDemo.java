import java.util.ArrayList;

public class ArrayListEqualsDemo {
    abstract static class Person {
        public String name;
        public Person(String name) {
            this.name = name;
        }
    }

    static class LostPerson extends Person {
        public LostPerson(String name) { super(name); }
    }

    static class FoundPerson extends Person {
        public FoundPerson(String name) { super(name); }

        public boolean equals(Object other) {
            if (this == other) { return true; }
            if (!(other instanceof Person)) { return false; }
            return ((Person) other).name.equals(this.name);
        }
    }

    @Override
    static class OverloadedPerson extends Person {
        public OverloadedPerson(String name) { super(name); }
        // does not override equals method because it does not have the EXACT
        // same parameter list
        public boolean equals(OverloadedPerson other) {
            if (null == other) { return false; }
            if (this == other) { return true; }
            if (!(other instanceof OverloadedPerson)) { return false; }
            return ((OverloadedPerson) other).name.equals(this.name);
        }
}

    public static void main(String[] args) {
        ArrayList peeps = new ArrayList();
    }
}
