package teka.android.organiks_platform_android.data.remote.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class TenantContext(
    private val auth: FirebaseAuth
) {
    fun getFruitCollection(): CollectionReference {
        val userId = auth.currentUser?.uid ?: throw IllegalStateException("User is not logged in")
        return FirebaseFirestore
            .getInstance()
            .collection("tenants")
            .document(userId)
            .collection(FirebaseConstants.FRUIT_COLLECTIONS)
    }

    fun getCustomerCollection(): CollectionReference {
        val userId = auth.currentUser?.uid ?: throw IllegalStateException("User is not logged in")
        return FirebaseFirestore
            .getInstance()
            .collection("tenants")
            .document(userId)
            .collection(FirebaseConstants.CUSTOMER_COLLECTION)
    }

    fun getInvoiceCollection(): CollectionReference {
        val userId = auth.currentUser?.uid ?: throw IllegalStateException("User is not logged in")
        return FirebaseFirestore
            .getInstance()
            .collection("tenants")
            .document(userId)
            .collection(FirebaseConstants.INVOICE_COLLECTION)
    }
}
