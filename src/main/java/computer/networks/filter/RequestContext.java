package computer.networks.filter;

public class RequestContext {

    private static ThreadLocal<String> usernames = new ThreadLocal<>();

    private RequestContext() {
    }

    public static void init() {
        usernames.set("Marius Dima");
    }

    public static String getUsername() {
        return usernames.get();
    }

    public static void setUsernames(String username) {
        usernames.set(username);
    }
}
