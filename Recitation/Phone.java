public abstract class Phone implements BluetoothDevice {
    private String owner;
    private int number;

    public Phone(int number, String owner) {
        this.owner = owner;
        this.number = number;
    }

    public String getOwner() {
        return owner;
    }

    public abstract String getWebbrowser();

    public String setOwner(String owner) {
        this.owner = owner;
    }

    public void call(Phone p) {
        System.out.println("Calling " + p.owner);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void connect(BluetoothDevice device) {
        System.out.println("Conneced to " + device.getName());
    }

    public String getName() {
        return owner;
    }
}
