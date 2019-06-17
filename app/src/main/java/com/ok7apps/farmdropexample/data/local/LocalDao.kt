package com.ok7apps.farmdropexample.data.local

import android.arch.persistence.room.*
import com.ok7apps.farmdropexample.data.local.model.ProducerEntity
import io.reactivex.Flowable

@Dao
interface LocalDao {

    @Query("SELECT * from producer WHERE page = :page ORDER BY `order` ")
    fun getProducers(page: Int): Flowable<List<ProducerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveProducers(items: List<ProducerEntity>)

    @Query("DELETE FROM producer WHERE page >= :page ")
    fun clearRange(page: Int)

    @Query("DELETE FROM producer")
    fun clear()

    @Transaction
    fun updateProducerRange(items: List<ProducerEntity>, page: Int) {
        clearRange(page)
        saveProducers(items)
    }

}