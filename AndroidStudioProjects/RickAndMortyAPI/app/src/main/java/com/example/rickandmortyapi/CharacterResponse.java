package com.example.rickandmortyapi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterResponse {
    @SerializedName("results")
    private List<Character> results;

    public List<Character> getResults() {
        return results;
    }
}
