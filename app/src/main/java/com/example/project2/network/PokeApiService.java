package com.example.project2.network;

import com.example.project2.network.models.PokemonListResponse;
import com.example.project2.network.models.PokemonResponse;
import com.example.project2.network.models.PokemonSpeciesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiService {
    @GET("pokemon")
    Call<PokemonListResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}")
    Call<PokemonResponse> getPokemonById(@Path("id") int id);

    @GET("pokemon-species/{id}")
    Call<PokemonSpeciesResponse> getPokemonSpeciesById(@Path("id") int id);
}
