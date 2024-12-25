package com.unir.listatarefasapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Tarefa> tarefaList;
    private TarefaAdapter tarefaAdapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fabAddTarefa);
        tarefaList = new ArrayList<>();

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tarefaAdapter = new TarefaAdapter(tarefaList);
        recyclerView.setAdapter(tarefaAdapter);

        // Configurar FAB
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TarefaActivity.class);
            startActivity(intent);
        });

        // Configurar clique na lista
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Tarefa tarefa = tarefaList.get(position);
                                Intent intent = new Intent(MainActivity.this, TarefaActivity.class);
                                intent.putExtra("tarefa", tarefa);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Tarefa tarefa = tarefaList.get(position);
                                TarefaDAO dao = new TarefaDAO(MainActivity.this);
                                dao.deletar(tarefa);
                                carregarTarefas();
                            }
                        }
                )
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarTarefas();
    }

    private void carregarTarefas() {
        TarefaDAO dao = new TarefaDAO(this);
        tarefaList.clear();
        tarefaList.addAll(dao.listar());
        tarefaAdapter.notifyDataSetChanged();
    }
}