package com.example.expensemanager

import androidx.room.*


@Dao
interface transactionDataobject {

    @Query("SELECT * from `transaction`")
    fun getAll(): List<transactionDataHold>

    @Insert
    fun insertAll(vararg  transactionDataHold: transactionDataHold)

    @Delete
    fun delete(transactionDataHold:  transactionDataHold)

    @Update
    fun update(vararg  transactionDataHold: transactionDataHold)
}