package com.example.sofascoreacademy.project.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.sofascoreacademy.project.model.BaseCity
import com.example.sofascoreacademy.project.model.LocationDb
import com.example.sofascoreacademy.project.model.Locations
import com.example.sofascoreacademy.project.model.Recent

@Database(entities = [Locations::class, Recent::class, BaseCity::class, LocationDb::class], version = 5, exportSchema = false)

abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        private var instance: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase? {
            if (instance == null) {
                instance = buildDatabase(context)
            }
            return instance
        }

        private fun buildDatabase(context: Context): WeatherDatabase {
            val MIGRATION_1_2: Migration = object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(" CREATE TABLE IF NOT EXISTS Recent(\n" +
                            "                    woeid INTEGER PRIMARY KEY NOT NULL,\n" +
                            "                    location_type TEXT NOT NULL,\n" +
                            "                    title TEXT NOT NULL,\n" +
                            "                    latt_long TEXT NOT NULL\n" +
                            "                )")
                }
            }
            val MIGRATION_2_3: Migration = object : Migration(2, 3) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    // Remove the table
                    database.execSQL("DROP TABLE City")
                }
            }

            val MIGRATION_3_4: Migration = object : Migration(3, 4) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    // create
                    database.execSQL(" CREATE TABLE IF NOT EXISTS BaseCity(\n" +
                            "                    latt_long TEXT PRIMARY KEY NOT NULL,\n" +
                            "                    title TEXT NOT NULL\n" +
                            "                )")
                }
            }

            val MIGRATION_4_5: Migration = object : Migration(4, 5) {
                //add column
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(" CREATE TABLE IF NOT EXISTS LocationDb(\n" +
                            "                    woeid INTEGER PRIMARY KEY NOT NULL,\n" +
                            "                    location_type TEXT NOT NULL,\n" +
                            "                    title TEXT NOT NULL,\n" +
                            "                    latt_long TEXT NOT NULL,\n" +
                            "                    position INTEGER NOT NULL\n" +
                            "                )")
                }
            }

            return Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "WeatherDatabase.db"
            ).addMigrations(MIGRATION_1_2).addMigrations(MIGRATION_2_3).addMigrations(MIGRATION_3_4).addMigrations(MIGRATION_4_5).build()
        }
    }

}