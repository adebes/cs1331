public class IPhone extends Phone {
    private String version;
    private String ringtone;
    public IPhone(int number, String owner, String version) {
        // defaults to super()
        super(number, owner);
        this.version = version;
    }

    public IPhone() {
        this(0, "", "");
    }

    public void faceTime(IPhone p) {
        System.out.println("Facetiming " + p.getOwner());
    }

    @Override
    public String getWebbrowser() {
        return "Safari";
    }

    @Override
    public void call(Phone p) {
        if (ringtone != p.getRingtone) {
            System.out.println(ringtone + " calling " + p.getOwner());
        } else {
            super.call(p.getOwner());
        }
    }

    public String getVersion() {
        return version;
    }

    public String getRingtone() {
        return ringtone;
    }

    public String setRingtone(String ringtone) {
        this.ringtone = ringtone;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
