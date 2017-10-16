public class Android extends Phone {
    public Android(int number, String owner) {
        super(number, owner);
    }

    @Override
    public String getWebbrowser() {
        return "Chrome";
    }
}
