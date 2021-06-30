package com.fortesfilmes.view;

import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fortesfilmes.R;
import com.fortesfilmes.adapter.MovieAdapter;
import com.fortesfilmes.interfaces.Interfaces;
import com.fortesfilmes.model.MovieModel;
import com.fortesfilmes.service.RestApiInterface;
import com.fortesfilmes.service.RestApiService;
import com.fortesfilmes.service.RoomDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

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


public class MoveDetail extends AppCompatActivity {

    //
    private RoomDB roomDB;
    private ExecutorService executor;
    private Handler handler;

    //
    private ImageView ivCover;
    private TextView tvTitle;
    private TextView tvDesc;
    private TextView tvReleased;
    private TextView tvRuntime;
    private TextView tvGenre;
    private TextView tvDirector;
    private TextView tvWriter;
    private FloatingActionButton fabFavorite;

    //
    private MovieModel selectedMovie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        roomDB = RoomDB.getInstance(getApplicationContext());
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(getApplicationContext().getMainLooper());

        ivCover = (ImageView) findViewById(R.id.iv_cover);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        tvReleased = (TextView) findViewById(R.id.tv_released);
        tvRuntime = (TextView) findViewById(R.id.tv_runtime);
        tvGenre = (TextView) findViewById(R.id.tv_genre);
        tvDirector = (TextView) findViewById(R.id.tv_director);
        tvWriter = (TextView) findViewById(R.id.tv_writer);
        fabFavorite = (FloatingActionButton) findViewById(R.id.fab_favorite);
        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.println(Log.ERROR, "fabFavorite", "onClick");
                //
                if (selectedMovie.isFavorite()) {
                    selectedMovie.setFavorite(false);
                    fabFavorite.setImageResource(R.mipmap.icon_heart);
//            fabFavorite.setImageDrawable(getResources().getDrawable(R.mipmap.icon_heart_red));
                } else {
                    selectedMovie.setFavorite(true);
                    fabFavorite.setImageResource(R.mipmap.icon_heart_red);
                }

                //
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        roomDB.movieDao().update(selectedMovie);
                        printMsg("roomDB.update: " + selectedMovie.getTitle());
//                        handler.post(new Runnable() {
//                            public void run() {
//                                updateView(selectedMovie);
//                            }
//                        });
                    }
                });
            }
        });

        //
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (int) (displayMetrics.heightPixels * 0.8);
//        int width = displayMetrics.widthPixels;

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);
        ivCover.setLayoutParams(layoutParams);

//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height);
//        ivCover.setLayoutParams(layoutParams);


        String movieTitle = getIntent().getExtras().getString(MainActivity.ACTIVITY_DATA_MOVIE_TITLE);
        if (movieTitle != null) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    selectedMovie = roomDB.movieDao().findByTitle(movieTitle);
//                    selectedMovie = findMovieByTitle(movieTitle);
                    handler.post(new Runnable() {
                        public void run() {
                            updateView(selectedMovie);
                        }
                    });
                }
            });
        }
    }

//    public MovieModel findMovieByTitle(String title) {
//        MovieModel movie = roomDB.movieDao().findByTitle(title);
//        printMsg("movie: " + movie.getTitle());
//        return movie;
//    }

    private void updateView(MovieModel movie) {

        Picasso.get()
                .load(movie.getPoster())
                .fit().centerCrop()
                .into(ivCover);

        tvTitle.setText(movie.getTitle());
        tvDesc.setText(movie.getPlot());
        tvReleased.setText(movie.getReleased());
        tvRuntime.setText(movie.getRuntime());
        tvGenre.setText(movie.getGenre());
        tvDirector.setText(movie.getDirector());
        tvWriter.setText(movie.getWriter());
        //
        if (movie.isFavorite()) {
            fabFavorite.setImageResource(R.mipmap.icon_heart_red);
//            fabFavorite.setImageDrawable(getResources().getDrawable(R.mipmap.icon_heart_red));
        } else {
            fabFavorite.setImageResource(R.mipmap.icon_heart);
        }
    }

    private void printMsg(String msg) {
        handler.post(new Runnable() {
            public void run() {
                Log.println(Log.ERROR, "printMsg", msg);
                Toast.makeText(getApplicationContext(), "printMsg: " + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

//                    handler.post(new Runnable() {
//                        public void run() {
//                            movieAdapter.updateList(movieList);
//                            Log.println(Log.ERROR, "MainActivity", "updateList");
//                            Toast.makeText(getApplicationContext(), "movieList: " + movieList.size(), Toast.LENGTH_SHORT).show();
//                        }
//                    });