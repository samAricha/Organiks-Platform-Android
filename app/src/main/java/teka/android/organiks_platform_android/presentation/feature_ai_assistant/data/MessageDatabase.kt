package teka.android.organiks_platform_android.presentation.feature_ai_assistant.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Message::class, AnalystMessage::class],
    version = 2
)
abstract class MessageDatabase : RoomDatabase() {
    abstract val dao: MessageDao
}