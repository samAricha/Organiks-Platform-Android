package teka.android.organiks_platform_android.data.remote.firebase

import com.google.firebase.firestore.FirebaseFirestore

suspend fun <T : Any> uploadToFirebase(
    collectionPath: String,
    data: T,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    try {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection(collectionPath)
            .add(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    } catch (e: Exception) {
        onFailure(e)
    }
}

