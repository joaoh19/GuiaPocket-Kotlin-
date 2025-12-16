package com.example.guiabairro.data.dao

import androidx.room.*
import com.example.guiabairro.models.Estabelecimento
import kotlinx.coroutines.flow.Flow

@Dao
interface EstabelecimentoDao {

    @Query("SELECT * FROM estabelecimentos ORDER BY nome ASC")
    fun getAll(): Flow<List<Estabelecimento>>

    @Query("SELECT * FROM estabelecimentos WHERE id = :id")
    suspend fun getById(id: Int): Estabelecimento?

    @Query("SELECT * FROM estabelecimentos WHERE nome LIKE '%' || :filter || '%' OR tipo LIKE '%' || :filter || '%'")
    suspend fun search(filter: String): List<Estabelecimento>

    @Insert
    suspend fun insert(estabelecimento: Estabelecimento): Long

    @Update
    suspend fun update(estabelecimento: Estabelecimento)

    @Delete
    suspend fun delete(estabelecimento: Estabelecimento)

    @Query("DELETE FROM estabelecimentos")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM estabelecimentos")
    suspend fun count(): Int
}