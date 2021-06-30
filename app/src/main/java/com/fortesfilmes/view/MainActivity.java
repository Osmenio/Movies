package com.fortesfilmes.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.fortesfilmes.R;
import com.fortesfilmes.adapter.MovieAdapter;
import com.fortesfilmes.dao.MovieDao;
import com.fortesfilmes.interfaces.Interfaces;
import com.fortesfilmes.model.MovieModel;
import com.fortesfilmes.service.RestApiInterface;
import com.fortesfilmes.service.RestApiService;
import com.fortesfilmes.service.RoomDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//● Consultar API (https://private-b34167-rvmarvel.apiary-mock.com/saga) e fazer a listagem dos 23 filmes da Marvel, que fazem parte da Infinity Saga, mostrando título, data de lançamento, gênero e miniatura do poster.
//● Possibilidade de selecionar um filme e visualizar a sua versão completa, com poster expandido e com a descrição do filme.
//● Possibilidade de filtrar um filme da lista.
//● Permitir o acesso a lista de filmes quando o usuário estiver offline.
//● Possibilidade de ordenar a lista do melhor para o pior e salvar a preferência.
//● Outras ideias que você achar interessante. O desafio poderá ser realizado em Kotlina Java. Sugerimos utilizar componentes do Android Jetpack (ViewModel, LiveData, Room, etc.). O layout é livre, porém demonstrar boas noções de UI/UX será muito bom.
//● Implementar testes unitários.


public class MainActivity extends AppCompatActivity {

    //    private Context context;
    public static final String ACTIVITY_DATA_MOVIE_TITLE = "ACTIVITY_DATA_MOVIE_TITLE";

    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;

    private Button btnClick;

    //
//    List<MovieModel> movieList = null;

    //
    private RoomDB roomDB;
    private ExecutorService executor;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        context = this;
        roomDB = RoomDB.getInstance(getApplicationContext());
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(getApplicationContext().getMainLooper());

        recyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);
        setupRecycler();

        btnClick = (Button) findViewById(R.id.bt_click);
        btnClick.setOnClickListener(view -> {
//            List<MovieModel> listDb = roomDB.movieDao().findAll();
//            Toast.makeText(getApplicationContext(), "listDb: " + listDb.size(), Toast.LENGTH_SHORT).show();

        });

        executor.execute(new Runnable() {
            @Override
            public void run() {
                getAllMoviesFromServer();
                Log.println(Log.ERROR, "MainActivity", "getAllMoviesFromServer");
            }
        });
    }


    private void setupRecycler() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        movieAdapter = new MovieAdapter(new ArrayList<>(0));
        recyclerView.setAdapter(movieAdapter);

        movieAdapter.setOnClickListener(new Interfaces.OnClickAdapter() {
            @Override
            public void onClickAdapterLintener(Object arg) {
                MovieModel movie = (MovieModel) arg;
                Toast.makeText(getApplicationContext(), "Movie: " + movie.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MoveDetail.class);
                intent.putExtra(ACTIVITY_DATA_MOVIE_TITLE, movie.getTitle());
                startActivity(intent);
            }
        });
    }


    public void getAllMoviesFromServer() {

        RestApiInterface restApi = RestApiService.createService(RestApiInterface.class);
        Call<List<MovieModel>> call = restApi.getMovies();
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                if (response.isSuccessful()) {
//                    List<MovieModel> movieList = response.body();
//                    movieList = response.body();
//                    if (movieList != null) {
                    if (response.body() != null) {
                        Log.println(Log.ERROR, "MainActivity", "response.body()");
                        List<MovieModel> movieList = response.body();
                        movieAdapter.updateList(movieList);

                        //
                        persistAllMovies(movieList);
//                        movieList = response.body();
//                        movieAdapter.updateList(movieList);
                    } else {
//                        movieList = null;
                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Resposta não foi sucesso", Toast.LENGTH_SHORT).show();
                    ResponseBody errorBody = response.errorBody();
                }
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void persistMovie(MovieModel movie) {
//        roomDB.movieDao().persist(movie);
//    }
//
//    public void persistAllMovies(List<MovieModel> movies) {
//        for (MovieModel movie : movies) {
//            roomDB.movieDao().persist(movie);
//        }
//    }

    public void persistAllMovies(List<MovieModel> movies) {

//        for (MovieModel movie : movies) {
//            roomDB.movieDao().persist(movie);
//        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (MovieModel movie : movies) {
                    try {
                        roomDB.movieDao().persist(movie);
                    } catch (Exception throwables) {
                        Log.println(Log.ERROR, "SQLException", "movie: " + movie.getTitle());
//                        throwables.printStackTrace();
                    }
                }
            }
        });
    }
}