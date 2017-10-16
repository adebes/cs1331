public interface BluetoothDevice {
    public void connect(BluetoothDevice device);
    public String getName();

    public default void printName() {
        System.out.println(getName());
    }
}
