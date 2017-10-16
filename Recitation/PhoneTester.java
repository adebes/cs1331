public class PhoneTester {
    public static void main(String[] args) {
        IPhone a = new IPhone(3, "Luke", "4s");
        IPhone b = new IPhone(5, "Sean", "X");
        Android c = new Android(5, "Chris");
        System.out.println(a.getOwner());
        a.call(b);
        a.setRingtone("Chrip");
        a.faceTime(b);
        // Computer comp = new Computer("Rob");
        // comp.connect(a);
        // comp.connect(b);
        // comp.printName();
    }
}
