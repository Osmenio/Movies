package com.fortesfilmes.view;

import android.os.Bundle;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

    }


}