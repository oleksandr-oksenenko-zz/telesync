package smartfoxlabs.telesync.Rest;

/**
 * Created by dwite_000 on 13.10.2014.
 */
public class RegRequest {
    final String deviceName;
    final String tvName;

    public RegRequest(String foo, String bar) {
        this.deviceName = foo;
        this.tvName = bar;
    }
}