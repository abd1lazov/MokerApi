package com.example.mokerapi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mokerapi.R;
import com.example.mokerapi.adapter.MokerAdapter;
import com.example.mokerapi.data.MokerModel;
import com.example.mokerapi.databinding.ActivityMainBinding;
import com.example.mokerapi.network.RetrofitBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MokerAdapter.Callback {

    private ActivityMainBinding binding;
    private final MokerAdapter adapter = new MokerAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerView.setAdapter(adapter);

        getPosts();
        //createPosts(mokerModel);
        openSecondActivity();
    }




    private void openSecondActivity() {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createPosts(MokerModel mokerModel) {

        mokerModel = new MokerModel("post","second",2,4);

        RetrofitBuilder.getInstance().createMokerModel(mokerModel).enqueue(new Callback<MokerModel>() {
            @Override
            public void onResponse(Call<MokerModel> call, Response<MokerModel> response) {

            }

            @Override
            public void onFailure(Call<MokerModel> call, Throwable t) {

            }
        });
    }

    private void getPosts() {
        RetrofitBuilder.getInstance().getPosts().enqueue(new Callback<List<MokerModel>>() {
            @Override
            public void onResponse(Call<List<MokerModel>> call, Response<List<MokerModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("tag", "success " + response.body());
                    adapter.addItems(response.body());
                } else {
                    Log.d("tag", "error " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<MokerModel>> call, Throwable t) {

                Log.d("tag", "failure " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void Mokerclick(MokerModel mokerModel) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("id", "" + mokerModel.getId());
        intent.putExtra("title", mokerModel.getTitle());
        intent.putExtra("content", mokerModel.getContent());
        intent.putExtra("user", "" + mokerModel.getUser());
        intent.putExtra("group", "" + mokerModel.getGroup());
        startActivity(intent);
    }
}


//        ДЗ / Дедлайн 13 - Июля (Пятница)
//        1. Получить все посты. Отобразить их в RecyclerView
//        2. Удалить пост. (Долгий тап по посту. Открывается диалоговое окно с кнопками Да/Отменить. По кнопке да пост удаляется)
//        3. Добавить кнопку “Добавить” по нажатию на нее открывается новый экран с полями "title", "content", "user", "group" после их заполнения и нажатия на кнопку отправить.
//        Новый пост (элемент) должен быть создан на бэке
//        4. По нажатию на пост должен открываться новый экран с информацией только об этом посте (элементе).
//        Все поля должны быть редактируемыми внизу должна быть кнопка “Изменить”. По нажатию на нее на бэке должен измениться только этот пост (элемент)
//        5. Разобраться с WeatherApi - https://openweathermap.org/current
//        6. Начать верстать макеты. Макеты (см. В доке)