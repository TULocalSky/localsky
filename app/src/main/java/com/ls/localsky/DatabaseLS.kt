package com.ls.localsky

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

class DatabaseLS() {

    private val database = Firebase.firestore
    private val auth = Firebase.auth

    /**
        Creates a user in the database
        @param firstName - The first name of the user
        @param lastName - The last name of the user
        @param email - The email address of the user
        @param password - The password provided by the user
        @param onSuccess - A lambda expression that gives access to the authenticated user on success
        @param firstName - A lambda express that gives access to the error when either authentication or user creation fails
        @return void

        *Notes*
        if email address is already in use this will be put in the onFailure callback
         com.google.firebase.auth.FirebaseAuthUserCollisionException:
          The email address is already in use by another account.
     **/
    fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        onSuccess: (FirebaseUser, User) -> Unit,
        onFailure: (Exception) -> Unit
    ){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                if(task.isSuccessful){
                    val userMap = hashMapOf(
                        "UserID" to auth.currentUser!!.uid,
                        "FirstName" to firstName,
                        "LastName" to lastName,
                        "Email" to email
                    )

                    val user = User(
                        auth.currentUser!!.uid,
                        firstName,
                        lastName,
                        email
                    )

                    database.collection("Users")
                        .add(userMap)
                        .addOnSuccessListener {
                            Log.d(TAG_FIREAUTH, "User Created with ID $it")
                            onSuccess(auth.currentUser!!, user)
                        }
                        .addOnFailureListener{ e ->
                            Log.w(TAG_FIREAUTH, "Error adding document", e)
                            onFailure(e)
                        }
                } else {
                    onFailure(task.exception!!)
                }
            }
    }

    /**
    Creates a user in the database
    @param email - The email address of the user
    @param password - The password provided by the user
    @param onSuccess - A lambda expression that gives access to the authenticated user on success
    @param firstName - A lambda express that gives access to the error when either authentication or user sign in fails
    @return void

     *Notes*
    if email address is already in use this will be put in the onFailure callback
    com.google.firebase.auth.FirebaseAuthUserCollisionException:
    The email address is already in use by another account.
     **/
    fun signIn(
        email: String,
        password: String,
        onSuccess: (FirebaseUser) -> Unit,
        onFailure: (Exception) -> Unit
    ){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    Log.d(TAG_FIREAUTH, "signInWithEmail:success")
                    onSuccess(auth.currentUser!!)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG_FIREAUTH, "signInWithEmail:failure", task.exception)
                    onFailure(task.exception!!)
                }
            }
    }

    /**

     **/
    fun getUserTable(
        callback: (QuerySnapshot?) -> Unit
    ) {
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
    fun getUserByID(
        user: User,
        callback: (QueryDocumentSnapshot?) -> Unit
    ) {
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

    /**

     **/
    fun createUserReport(
        user:User,
        createdTime: String,
        latitude: Double,
        longitude: Double,
        locationPicture: String,
        weatherCondition: String,
        onSuccess: (DocumentReference, UserReport) -> Unit,
        onFailure: (Exception) -> Unit
    ){
        val report = hashMapOf(
            "UserID" to user.userID,
            "CreatedAt" to createdTime,
            "Latitude" to latitude,
            "Longitude" to longitude,
            "WeatherCondition" to weatherCondition,
            "Picture" to locationPicture,
        )
        database.collection("UserReports")
            .add(report)
            .addOnSuccessListener {
                Log.d(TAG_FIRESTORE, "UserReport Created with ID $it")
                val userReport = UserReport(
                    it.id,
                    user,
                    createdTime,
                    latitude,
                    longitude,
                    locationPicture,
                    weatherCondition
                )
                onSuccess(it, userReport)
            }
            .addOnFailureListener{ e ->
                Log.w(TAG_FIREAUTH, "Error adding document", e)
                onFailure(e)
            }
    }

    /**

     **/
    fun getAllUserReports(){

    }


    companion object{
        private val TAG_FIRESTORE = "FIRESTORE"
        private val TAG_FIREAUTH = "FIREAUTH"
    }
}