package teka.android.organiks_platform_android.presentation.feature_ai_assistant.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MessageDao {
    @Upsert
    suspend fun upsertMessage(message: Message)

    @Query("SELECT * FROM message")
    fun getAllMessage(): LiveData<List<Message>>

    @Query("DELETE FROM message")
    suspend fun deleteAllMessages()
}