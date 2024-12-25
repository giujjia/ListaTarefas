package com.example.rickandmortyapi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RickMortyAPI {
    @GET("character")
    Call<CharacterResponse> getCharacterByName(@Query("name") String name);
}
