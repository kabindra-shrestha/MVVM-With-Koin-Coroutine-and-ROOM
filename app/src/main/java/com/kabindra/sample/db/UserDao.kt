package com.kabindra.sample.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kabindra.sample.db.model.UserData

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun findAll(): List<UserData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(users: List<UserData>)

    @Query("DELETE FROM User")
    suspend fun deleteAllUser()
}