package app.task.com.softTsk.Network;

import app.task.com.softTsk.Model.ArticleResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiInterface {

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET("all-sections/7.json?apikey=828e64b670894f49b24617fa4fdbc1d4")
    Call<ArticleResponse> getArticles();


}
