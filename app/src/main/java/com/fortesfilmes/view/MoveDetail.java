package com.fortesfilmes.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.fortesfilmes.R;
import com.fortesfilmes.model.MovieModel;
import com.fortesfilmes.service.RoomDB;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//● Consultar API (https://private-b34167-rvmarvel.apiary-mock.com/saga) e fazer a listagem dos 23 filmes da Marvel, que fazem parte da Infinity Saga, mostrando título, data de lançamento, gênero e miniatura do poster.
//● Possibilidade de selecionar um filme e visualizar a sua versão completa, com poster expandido e com a descrição do filme.
//● Possibilidade de filtrar um filme da lista.
//● Permitir o acesso a lista de filmes quando o usuário estiver offline.
//● Possibilidade de ordenar a lista do melhor para o pior e salvar a preferência.
//● Outras ideias que você achar interessante. O desafio poderá ser realizado em Kotlina Java. Sugerimos utilizar componentes do Android Jetpack (ViewModel, LiveData, Room, etc.). O layout é livre, porém demonstrar boas noções de UI/UX será muito bom.
//● Implementar testes unitários.


public class MoveDetail extends AppCompatActivity {

    //
    private Toolbar toolbar;

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
    private ImageView ivFavorite;
    private RatingBar rbRating;
//    private FloatingActionButton fabFavorite;

    //
    private MovieModel selectedMovie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.detail);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.string.color_primary))));

        //
        roomDB = RoomDB.getInstance(getApplicationContext());
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(getApplicationContext().getMainLooper());

        //
        ivCover = (ImageView) findViewById(R.id.iv_cover);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        tvReleased = (TextView) findViewById(R.id.tv_released);
        tvRuntime = (TextView) findViewById(R.id.tv_runtime);
        tvGenre = (TextView) findViewById(R.id.tv_genre);
        tvDirector = (TextView) findViewById(R.id.tv_director);
        tvWriter = (TextView) findViewById(R.id.tv_writer);
        ivFavorite = (ImageView) findViewById(R.id.iv_favorite);
        rbRating = (RatingBar) findViewById(R.id.rb_rating);


        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                if (selectedMovie.isFavorite()) {
                    selectedMovie.setFavorite(false);
                    ivFavorite.setImageResource(R.mipmap.icon_heart);
                    Toast.makeText(getApplicationContext(), "Filme removido dos favoritos", Toast.LENGTH_SHORT).show();
                } else {
                    selectedMovie.setFavorite(true);
                    ivFavorite.setImageResource(R.mipmap.icon_heart_red);
                    Toast.makeText(getApplicationContext(), "Filme adicionado aos favoritos", Toast.LENGTH_SHORT).show();
                }
                //
                updateMovie(selectedMovie);
            }
        });

        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                selectedMovie.setRating(rating);
                updateMovie(selectedMovie);
//                Toast.makeText(getApplicationContext(), "RatingBar: " + rating, Toast.LENGTH_SHORT).show();
            }
        });

        //
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (int) (displayMetrics.heightPixels * 0.8);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);
        ivCover.setLayoutParams(layoutParams);

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
        if (movie.isFavorite()) {
            ivFavorite.setImageResource(R.mipmap.icon_heart_red);
        } else {
            ivFavorite.setImageResource(R.mipmap.icon_heart);
        }
        rbRating.setRating(movie.getRating());
    }

    private void updateMovie(MovieModel movie) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                roomDB.movieDao().update(movie);
            }
        });
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