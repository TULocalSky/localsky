package com.ls.localsky

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

class DatabaseLS() {

    private val database = Firebase.firestore

    /*

     */
    fun createUser(newUser: User){
        val user = hashMapOf(
            "UserID" to newUser.userID,
            "FirstName" to newUser.firstName,
            "LastName" to newUser.lastName,
            "Email" to newUser.email
        )

        database.collection("Users")
            .add(user)
            .addOnSuccessListener {
                Log.d(TAG_FIREAUTH, "User Created with ID $it")
            }
            .addOnFailureListener{ e ->
                Log.w(TAG_FIREAUTH, "Error adding document", e)
            }
    }

    /*

     */
    fun getUserTable(callback: (QuerySnapshot?) -> Unit) {
        database.collection("Users")
            .get()
            .addOnSuccessListener { result ->
                callback(result)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG_FIRESTORE, "Error getting documents.", exception)
                callback(null)
            }
    }

    /*

     */
    fun getUserByID(user: User, callback: (QueryDocumentSnapshot?) -> Unit) {
        getUserTable { users ->
            if (users != null) {
                for (document in users) {
                    if (document.data[User.USERID] == user.userID) {
                        callback(document)
                        return@getUserTable
                    }
                }
            }
            callback(null)
        }
    }

    /*

     */
    fun createMarker(){

    }

    companion object{
        private val TAG_FIRESTORE = "FIRESTORE"
        private val TAG_FIREAUTH = "FIREAUTH"
    }
}