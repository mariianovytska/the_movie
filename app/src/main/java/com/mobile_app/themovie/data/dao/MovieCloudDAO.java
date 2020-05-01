package com.mobile_app.themovie.data.dao;

import android.util.Log;

import com.google.common.base.Optional;
import com.mobile_app.themovie.BuildConfig;
import com.mobile_app.themovie.data.entity.CloudResponse;
import com.mobile_app.themovie.data.entity.Movie;
import com.mobile_app.themovie.data.entity.Video;
import com.mobile_app.themovie.data.entity.VideoResponse;
import com.mobile_app.themovie.domain.error.HostUnavailableException;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MovieCloudDAO {

    private ThemoviedbEndpoint endpoint;
    private String language;

    public MovieCloudDAO(String language) {
        this.language = language;
        endpoint = ThemoviedbClient.getClient().create(ThemoviedbEndpoint.class);
    }

    public Observable<CloudResponse<Movie>> getAll(Integer page) {
        return endpoint.getMovies(
                BuildConfig.API_KEY,
                language,
                page
        );
    }

    public Single<Optional<Movie>> get(int id) {
        return getMovie(id).first(Optional.absent());
    }

    private Observable<Optional<Movie>> getMovie(int id) {
        return endpoint.getMovie(
                id,
                BuildConfig.API_KEY,
                language
        ).map(movie ->
                movie == null ? Optional.absent() : Optional.of(movie));
    }

    public Observable<CloudResponse<Movie>> search(Integer page, String query) {
        return endpoint.search(
                BuildConfig.API_KEY,
                language,
                page,
                query,
                false
        );
    }

    public Observable<VideoResponse<Video>> getVideo(Integer movieId) {
        return endpoint.getVideo(
                movieId,
                BuildConfig.API_KEY,
                language
        ).doOnError(e -> {
            throw new HostUnavailableException();
        });
    }

    private interface ThemoviedbEndpoint {
        String API_KEY = "api_key";
        String PAGE = "page";
        String LANGUAGE = "language";
        String QUERY = "query";
        String INCLUDE_ADULT = "include_adult";

        @GET("movie/popular?")
        Observable<CloudResponse<Movie>> getMovies(
                @Query(API_KEY) String key,
                @Query(LANGUAGE) String language,
                @Query(PAGE) Integer page
        );

        @GET("movie/{id}?")
        Observable<Movie> getMovie(
                @Path("id") int id,
                @Query(API_KEY) String key,
                @Query(LANGUAGE) String language
        );

        @GET("search/movie?")
        Observable<CloudResponse<Movie>> search(
                @Query(API_KEY) String key,
                @Query(LANGUAGE) String language,
                @Query(PAGE) Integer page,
                @Query(QUERY) String query,
                @Query(INCLUDE_ADULT) Boolean includeAdult
        );

        @GET("movie/{id}/videos?")
        Observable<VideoResponse<Video>> getVideo(
                @Path("id") int id,
                @Query(API_KEY) String key,
                @Query(LANGUAGE) String language
        );
    }

    private static class ThemoviedbClient {
        private static Retrofit retrofit = null;

        static Retrofit getClient() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BuildConfig.BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }
}
