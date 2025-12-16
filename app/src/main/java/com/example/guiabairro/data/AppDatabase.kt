package com.example.guiabairro.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.guiabairro.data.dao.EstabelecimentoDao
import com.example.guiabairro.models.Estabelecimento

@Database(entities = [Estabelecimento::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun estabelecimentoDao(): EstabelecimentoDao

    companion object {
        @Volatile private var db: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            val currentDb = db
            if (currentDb != null) return currentDb

            return synchronized(this) {
                val newDb = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bairro_app.db" // Nome diferente
                ).fallbackToDestructiveMigration().build()

                db = newDb
                newDb
            }
        }


        @JvmStatic fun setTestInstance(testDb: AppDatabase) {
            db = testDb
        }
    }
}