package com.ls.localsky

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
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
                        User.USERID to auth.currentUser!!.uid,
                        User.FIRST_NAME to firstName,
                        User.LAST_NAME to lastName,
                        User.EMAIL_ADDRESS to email
                    )

                    val user = User(
                        auth.currentUser!!.uid,
                        firstName,
                        lastName,
                        email
                    )

                    database.collection(User.USER_TABLE)
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
    Sign in to with an email and password
    @param email - The email address of the user
    @param password - The password provided by the user
    @param onSuccess - A lambda expression that gives access to the authenticated user on success
    @param firstName - A lambda express that gives access to the error when either authentication or user sign in fails
    @return void

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

    fun signOut(){
        auth.signOut()
    }

    /**
     * Updates user information in the database.
     * @param user - The User object containing updated information.
     * @param onSuccess - A lambda expression called upon successful update.
     * @param onFailure - A lambda expression called upon failure to update.
     * @return void
     */
    fun updateUser(
        user: User,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ){
        val newUserData = hashMapOf(
            User.USERID to user.userID,
            User.FIRST_NAME to user.firstName,
            User.LAST_NAME to user.lastName,
            User.EMAIL_ADDRESS to user.email
        )
        getUserByID(
            user.userID,
            { userDocument ->
                database.collection(User.USER_TABLE)
                    .document(userDocument!!.id)
                    .set(newUserData, SetOptions.merge())
                onSuccess()
            },
            onFailure
        )
    }

    /**
     * Retrieves all user records from the database.
     * @param onSuccess - A lambda expression that receives the QuerySnapshot result.
     * @param onFailure - A lambda expression called upon failure to get the table.
     * @return void
     */
    fun getUserTable(
        onSuccess: (QuerySnapshot?) -> Unit,
        onFailure: () -> Unit
    ) {
        database.collection(User.USER_TABLE)
            .get()
            .addOnSuccessListener { result ->
                onSuccess(result)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG_FIRESTORE, "Error getting documents.", exception)
                onFailure()
            }
    }

    /**
     * Retrieves a user record from the database by user ID.
     * @param userID - The ID of the user to retrieve.
     * @param onSuccess - A lambda expression called with the QueryDocumentSnapshot upon success.
     * @param onFailure - A lambda expression called upon failure to retrieve the user.
     * @return void
     */
    fun getUserByID(
        userID: String,
        onSuccess: (QueryDocumentSnapshot?) -> Unit,
        onFailure: () -> Unit
    ) {
        getUserTable (
            { users ->
            if (users != null) {
                for (document in users) {
                    if (document.data[User.USERID] == userID) {
                        onSuccess(document)
                        return@getUserTable
                    }
                }
            }
                onFailure()
            },
            {
                //Choosing not to handle if the user table fails yet
            })
    }

    /**
     * @param user - The ID of the user to delete.
     * @param onSuccess - A lambda expression called with the Task on success.
     * @param onFailure - A lambda expression called upon failure to delete the user.
     * @return void
     */
    fun removeUser(
        userID: String,
        onSuccess: (Task<Void>) -> Unit,
        onFailure: (Exception) -> Unit
    ){
        getUserByID(
            userID,
            {
                database.collection(User.USER_TABLE)
                    .document(it!!.id)
                    .delete()
                    .addOnCompleteListener(onSuccess)
                    .addOnFailureListener(onFailure)
            },
            {
                //Need to handle this later but should not be a problem
            }
        )
    }

    /**
     * Creates a user report in the database.
     * @param user - The User object associated with the report.
     * @param createdTime - The time the report was created.
     * @param latitude - The latitude of the report location.
     * @param longitude - The longitude of the report location.
     * @param locationPicture - The picture associated with the report location.
     * @param weatherCondition - The weather condition at the report location.
     * @param onSuccess - A lambda expression called upon successful report creation.
     * @param onFailure - A lambda expression called upon failure to create the report.
     * @return void
     */
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
        database.collection(UserReport.USER_REPORT_TABLE)
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
     * Retrieves all user reports from the database.
     * @param callback - A lambda expression that receives the QuerySnapshot result.
     * @return void
     */
    fun getAllUserReports(callback: (QuerySnapshot?) -> Unit
    ) {
        database.collection(UserReport.USER_REPORT_TABLE)
            .get()
            .addOnSuccessListener { result ->
                callback(result)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG_FIRESTORE, "Error getting documents.", exception)
                callback(null)
            }
    }


    companion object{
        private val TAG_FIRESTORE = "FIRESTORE"
        private val TAG_FIREAUTH = "FIREAUTH"
    }
}