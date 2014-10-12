package smartfoxlabs.telesync.Rest;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by dwite_000 on 12.10.2014.
 */
public class TeleSyncClient {

    public static final String API_URL = "http:/test.api";

    public static class TV {
        String videoUrl;
        int tvId;
    }

    public interface TeleSyncRestApi {

        @GET("/reg/{name}/")
        TV getTv(@Path("name") String tvName);

        @POST("/status/{id}/")
        void updateStatus(@Path("id") int tvId);
    }

}
