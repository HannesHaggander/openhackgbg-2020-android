package com.towerowl.openhackgbg2020.data

import androidx.room.*
import com.towerowl.openhackgbg2020.models.User

@Dao
interface AuthenticationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): User?
}