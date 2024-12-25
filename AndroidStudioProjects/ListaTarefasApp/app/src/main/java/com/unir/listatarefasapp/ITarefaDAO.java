package com.unir.listatarefasapp;

import java.util.List;

public interface ITarefaDAO {
    boolean salvar(Tarefa tarefa);
    boolean atualizar(Tarefa tarefa);
    boolean deletar(Tarefa tarefa);
    List<Tarefa> listar();
}
