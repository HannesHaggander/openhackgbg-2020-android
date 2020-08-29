package com.towerowl.openhackgbg2020.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.towerowl.openhackgbg2020.models.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
//@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "openhackdb"

        fun create(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    abstract fun authenticationDao(): AuthenticationDao
}

object DatabaseConverters {

}