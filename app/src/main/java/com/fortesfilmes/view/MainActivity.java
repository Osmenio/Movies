package com.fortesfilmes.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.fortesfilmes.R;
import com.fortesfilmes.adapter.MovieAdapter;
import com.fortesfilmes.dialog.ProgressDialog;
import com.fortesfilmes.dialog.SortDialog;
import com.fortesfilmes.dialog.UserDialog;
import com.fortesfilmes.enumeration.SortEnum;
import com.fortesfilmes.interfaces.Interfaces;
import com.fortesfilmes.model.MovieModel;
import com.fortesfilmes.service.PreferenceService;
import com.fortesfilmes.service.RestApiInterface;
import com.fortesfilmes.service.RestApiService;
import com.fortesfilmes.service.RoomDB;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String ACTIVITY_DATA_MOVIE_TITLE = "ACTIVITY_DATA_MOVIE_TITLE";
    private Context context;

    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout srlContainer;

    //
    private Toolbar toolbar;
    //
    private RoomDB roomDB;
    private ExecutorService executor;
    private Handler handler;

    //
    List<MovieModel> movieList = null;
    private boolean isFavorite = false;

    //
    private UserDialog userDialog = null;
    private ProgressDialog progressDialog = null;

    //
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.home);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // DrawerLayout
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        context = this;
        roomDB = RoomDB.getInstance(getApplicationContext());
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(getApplicationContext().getMainLooper());

        recyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);
        loadAdapter();
        srlContainer = (SwipeRefreshLayout) findViewById(R.id.srl_container);
        srlContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkData();
                srlContainer.setRefreshing(false);
            }
        });

        checkData();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshDataView(null);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_home:
                toolbar.setTitle(R.string.home);
                isFavorite = false;
                refreshDataView(null);
                break;

            case R.id.nav_favorite:
                toolbar.setTitle(R.string.favorite);
                isFavorite = true;
                refreshDataView(null);
                break;

            case R.id.nav_sort:

                SortEnum sortConfig = PreferenceService.getInstance(getApplicationContext()).getSortConfig();
                SortDialog sortDialog = new SortDialog(context);
                sortDialog.setSelectedSort(sortConfig);
                sortDialog.setOnClickListener(new Interfaces.OnClickListener() {
                    @Override
                    public void onClickListenerCallback(Object arg) {
                        SortEnum sortEnum = (SortEnum) arg;
                        PreferenceService.getInstance(getApplicationContext()).setSortConfig(sortEnum);
                        refreshDataView(null);
                    }
                });
                sortDialog.show();
                break;

            case R.id.nav_about:
                Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_SHORT).show();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        //
        SearchView mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        mSearchView.setOnQueryTextListener(searchListener);
        return true;
    }

    private SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    List<MovieModel> list = roomDB.movieDao().searchByTitle(newText);
                    handler.post(new Runnable() {
                        public void run() {
                            movieAdapter.updateList(list);
                            Log.println(Log.ERROR, "searchByTitle: " + list.size(), "getAllMoviesFromServer");
                        }
                    });
                }
            });
            return false;
        }
    };

    private void loadAdapter() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        movieAdapter = new MovieAdapter(new ArrayList<>(0));
        recyclerView.setAdapter(movieAdapter);

        movieAdapter.setOnClickListener(new Interfaces.OnClickAdapter() {
            @Override
            public void onClickAdapterLintener(Object arg) {
                MovieModel movie = (MovieModel) arg;

                Intent intent = new Intent(getApplicationContext(), MoveDetail.class);
                intent.putExtra(ACTIVITY_DATA_MOVIE_TITLE, movie.getTitle());
                startActivity(intent);
            }
        });
    }

    private void checkData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<MovieModel> movieList = roomDB.movieDao().findAll();
                if (movieList.size() > 0) {
                    refreshDataView(movieList);
                } else {
                    if (isNetworkConnected()) {
                        handler.post(new Runnable() {
                            public void run() {
                                progressDialog = new ProgressDialog(context);
                                progressDialog.setTextDialog("Carregando informações do servidor");
                                progressDialog.show();
                            }
                        });
                        getAllMoviesFromServer();
                    } else {
                        handler.post(new Runnable() {
                            public void run() {
                                userDialog = new UserDialog(context);
                                userDialog.setTextDialog("O App precisa de conexão com internet");
                                userDialog.setLabelPositiveButton("OK");
                                userDialog.show();
                            }
                        });
                    }
                }
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
                    if (response.body() != null) {
                        Log.println(Log.ERROR, "getAllMoviesFromServer", "response.body()");
                        List<MovieModel> movieList = response.body();
                        //
                        persistAllMovies(movieList);
                        //
                        refreshDataView(movieList);
                        progressDialog.finish();
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

    public void persistAllMovies(List<MovieModel> movies) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (MovieModel movie : movies) {
                    try {
                        // TODO: random rating - remove
                       float rating = random.nextFloat() * 5;
                        movie.setRating(rating);
                        roomDB.movieDao().persist(movie);
                    } catch (Exception throwables) {
                        Log.println(Log.ERROR, "Exception", "Movie already exists into db: " + movie.getTitle());
//                        throwables.printStackTrace();
                    }
                }
            }
        });
    }

    public void refreshDataView(List<MovieModel> list) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (list == null) {
                    if (isFavorite) {
                        movieList = roomDB.movieDao().findAllFavorite();
                        Log.println(Log.ERROR, "refreshDataView: " + movieList.size(), "findAllFavorite");
                    } else {
                        movieList = roomDB.movieDao().findAll();
                        Log.println(Log.ERROR, "refreshDataView: " + movieList.size(), "findAll");
                    }
                } else {
                    movieList.clear();
                    movieList.addAll(list);
                }

                // Sort by
                SortEnum sortConfig = PreferenceService.getInstance(getApplicationContext()).getSortConfig();
                if (sortConfig == SortEnum.TITLE_ASC) {
                    Collections.sort(movieList, MovieModel.titleAsc);
                } else if (sortConfig == SortEnum.TITLE_DESC) {
                    Collections.sort(movieList, MovieModel.titleDesc);
                } else if (sortConfig == SortEnum.RATING_DESC) {
                    Collections.sort(movieList, MovieModel.ratingDesc);
                } else if (sortConfig == SortEnum.RATING_ASC) {
                    Collections.sort(movieList, MovieModel.ratingAsc);
                }

                handler.post(new Runnable() {
                    public void run() {
                        movieAdapter.updateList(movieList);
                        Log.println(Log.ERROR, "refreshDataView: " + movieList.size(), "updateList");
                    }
                });
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo().isConnected();
    }
}