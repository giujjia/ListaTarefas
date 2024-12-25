package com.example.rickandmortyapi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView textViewName, textViewStatus, textViewSpecie;
    private EditText edtBuscar;
    private Button btnBuscar;
    private ImageView imgCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textViewName = findViewById(R.id.textViewName);
        textViewStatus = findViewById(R.id.textViewStatus);
        textViewSpecie = findViewById(R.id.textViewSpecie);
        imgCharacter = findViewById(R.id.imgCharacter);
        btnBuscar = findViewById(R.id.btnBuscar);
        edtBuscar = findViewById(R.id.edtBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = edtBuscar.getText().toString();
                if(!query.isEmpty()){
                    fetchCharacter(query);
                }else{
                    Toast.makeText(MainActivity.this, "Por favor, insira um nome.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void fetchCharacter(String name){
        RickMortyAPI apiService = RetrofitClient.getClient().create(RickMortyAPI.class);
        Call<CharacterResponse> call = apiService.getCharacterByName(name);

        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().getResults().isEmpty()) {
                    Character character = response.body().getResults().get(0); // Primeiro resultado

                    // Atualizar Views
                    textViewName.setText(character.getName());
                    textViewStatus.setText("Status: " + character.getStatus());
                    textViewSpecie.setText("Espécie: " + character.getSpecies());

                    // Exibir imagem do personagem
                    Glide.with(MainActivity.this)
                            .load(character.getImage())
                            .into(imgCharacter);

                    textViewName.setVisibility(View.VISIBLE);
                    textViewStatus.setVisibility(View.VISIBLE);
                    textViewSpecie.setVisibility(View.VISIBLE);
                    imgCharacter.setVisibility(View.VISIBLE);
                } else {
                    Log.e("MainActivity", "Nenhum personagem encontrado.");
                    Toast.makeText(MainActivity.this, "Nenhum personagem encontrado.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                Log.e("MainActivity", "Erro: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}