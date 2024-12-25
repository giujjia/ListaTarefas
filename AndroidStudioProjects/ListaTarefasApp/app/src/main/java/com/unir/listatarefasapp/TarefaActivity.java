package com.unir.listatarefasapp;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class TarefaActivity extends AppCompatActivity {
    private EditText editTarefa;
    private Button btnSalvar;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);

        editTarefa = findViewById(R.id.editTarefa);
        btnSalvar = findViewById(R.id.btnSalvar);

        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefa");
        if (tarefaAtual != null) {
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }

        btnSalvar.setOnClickListener(v -> salvarTarefa());
    }

    private void salvarTarefa() {
        String nomeTarefa = editTarefa.getText().toString();
        if (nomeTarefa.isEmpty()) {
            editTarefa.setError("Preencha o nome da tarefa!");
            return;
        }

        TarefaDAO dao = new TarefaDAO(this);
        if (tarefaAtual != null) {
            tarefaAtual.setNomeTarefa(nomeTarefa);
            dao.atualizar(tarefaAtual);
        } else {
            Tarefa tarefa = new Tarefa();
            tarefa.setNomeTarefa(nomeTarefa);
            dao.salvar(tarefa);
        }
        finish();
    }
}