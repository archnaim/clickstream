package acn.clickstream.dsc.singleton;

public class Singleton {

    private static Singleton single_instance = null;

    public String BOUNCE_VISIT_PER_HOUR;

    public static Singleton getInstance() {
        // To ensure only one instance is created
        if (single_instance == null) {
            single_instance = new Singleton();
        }
        return single_instance;
    }
}
