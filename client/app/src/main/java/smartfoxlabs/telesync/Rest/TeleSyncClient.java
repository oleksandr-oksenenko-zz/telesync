package smartfoxlabs.telesync.Rest;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by dwite_000 on 12.10.2014.
 */
public class TeleSyncClient {

    public static final String API_URL = "http://telesync-crew4ok.rhcloud.com";

    public interface TeleSyncRestApi {

        @POST("/api/register/")
        TV regTv(@Body RegRequest body);

        @POST("/status/{id}/")
        void updateStatus(@Path("id") int tvId);
    }



}
