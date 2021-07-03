package com.fortesfilmes;

import com.fortesfilmes.model.MovieModel;
import com.fortesfilmes.service.RestApiInterface;
import com.fortesfilmes.service.RestApiService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RestApiServiceTest {

    private CountDownLatch latch;
    private MockWebServer server;

    private List<MovieModel> movieList;

    @Before
    public void setup() throws IOException {
        server = new MockWebServer();
        server.start();
        latch = new CountDownLatch(1);

        movieList = null;
    }

    @After
    public void shutdown() throws IOException {
        server.shutdown();
    }

    @Test
    public void MockResponseFileReaderTest() {
        String strFile = "";
        try {
            strFile = new MockResponseFileReader("test.json").getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(strFile.contains("success"));
    }

    @Test
    public void getMoviesTest() {

        try {
            // Config server
            server.enqueue(new MockResponse().setBody(new MockResponseFileReader("AllMovies.json").getContent()));
            HttpUrl baseUrl = server.url("");

            // Instance rest api
            RestApiInterface restApi = RestApiService.createService(RestApiInterface.class, baseUrl.url().toString());
            Call<List<MovieModel>> call = restApi.getMovies();
            call.enqueue(new Callback<List<MovieModel>>() {
                @Override
                public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                    if (response.isSuccessful()) {
                        movieList = response.body();
                    }
                    latch.countDown();
                }

                @Override
                public void onFailure(Call<List<MovieModel>> call, Throwable t) {
                    t.printStackTrace();
                    latch.countDown();
                }
            });

            //
            latch.await();
            assertNotNull(movieList);
            assertEquals("Iron Man", movieList.get(0).getTitle());

        } catch (IOException e) {
            assertTrue("IOException", false);
        } catch (InterruptedException e) {
            assertTrue("InterruptedException", false);
        }
    }
}