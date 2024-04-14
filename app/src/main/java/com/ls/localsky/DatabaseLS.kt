package com.ls.localsky

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.storage
import com.ls.localsky.models.User
import com.ls.localsky.models.UserReport
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime

class DatabaseLS() {

    private val database = Firebase.firestore
    private val auth = Firebase.auth
    private val storage = Firebase.storage

    /**
        Creates a user in the database
        @param firstName The first name of the user
        @param lastName The last name of the user
        @param email The email address of the user
        @param password The password provided by the user
        @param onSuccess A lambda expression that gives access to the authenticated user on success
        @param firstName A lambda express that gives access to the error when either authentication or user creation fails
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
     * Sign in to with an email and password
     * @param email The email address of the user
     * @param password The password provided by the user
     * @param onSuccess A lambda expression that gives access to the authenticated user on success
     * @param onFailure A lambda express that gives access to the error when either authentication or user sign in fails
     * @return void
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
     * @param user The User object containing updated information.
     * @param onSuccess A lambda expression called upon successful update.
     * @param onFailure A lambda expression called upon failure to update.
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
            user.userID!!,
            { userDocument, _ ->
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
     * @param onSuccess A lambda expression that receives the QuerySnapshot result.
     * @param onFailure A lambda expression called upon failure to get the table.
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
     * @param userID The ID of the user to retrieve.
     * @param onSuccess A lambda expression called with the QueryDocumentSnapshot upon success.
     * @param onFailure A lambda expression called upon failure to retrieve the user.
     * @return void
     */
    fun getUserByID(
        userID: String,
        onSuccess: (QueryDocumentSnapshot?, User?) -> Unit,
        onFailure: () -> Unit
    ) {
        getUserTable (
            { users ->
            if (users != null) {
                for (document in users) {
                    if (document.data[User.USERID] == userID) {
                        onSuccess(document, document.toObject<User>())
                        return@getUserTable
                    }
                }
            }
            },
            {
                onFailure
                //Choosing not to handle if the user table fails yet
            })
    }

    /**
     * @param user The ID of the user to delete.
     * @param onSuccess A lambda expression called with the Task on success.
     * @param onFailure A lambda expression called upon failure to delete the user.
     * @return void
     */
    fun removeUser(
        userID: String,
        onSuccess: (Task<Void>) -> Unit,
        onFailure: (Exception) -> Unit
    ){
        getUserByID(
            userID,
            { document, _ ->
                database.collection(User.USER_TABLE)
                    .document(document!!.id)
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
     * @param user The User object associated with the report.
     * @param createdTime The time the report was created.
     * @param latitude The latitude of the report location.
     * @param longitude The longitude of the report location.
     * @param locationPicture The picture associated with the report location.
     * @param weatherCondition The weather condition at the report location.
     * @param onSuccess A lambda expression called upon successful report creation.
     * @param onFailure A lambda expression called upon failure to create the report.
     * @return void
     */
    private fun createUserReport(
        user: User,
        createdTime: String,
        latitude: Double,
        longitude: Double,
        locationPicture: String,
        weatherCondition: String,
        onSuccess: (DocumentReference, UserReport) -> Unit,
        onFailure: (Exception) -> Unit
    ){
        val report = hashMapOf(
            UserReport.USER_ID to user.userID,
            UserReport.CREATED_TIME to createdTime,
            UserReport.LATITUDE to latitude,
            UserReport.LONGITUDE to longitude,
            UserReport.WEATHER_CONDITION to weatherCondition,
            UserReport.LOCATION_PICTURE to locationPicture,
        )
        database.collection(UserReport.USER_REPORT_TABLE)
            .add(report)
            .addOnSuccessListener {
                Log.d(TAG_FIRESTORE, "UserReport Created with ID $it")
                val userReport = UserReport(
                    it.id,
                    user.userID!!,
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

    private fun uploadImage(
        image: Bitmap,
        userID: String,
        fileName: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ){
        val storageReference = storage.reference
        val imageRef = storageReference.child("UserReportImages/$userID/$fileName.jpeg")
        val byteOutput = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteOutput)
        val data = byteOutput.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    onSuccess(imageRef.path)
                }
            }.addOnFailureListener{
                Log.d(TAG_STORAGE, it.toString())
                onFailure(it)
            }
    }

    fun uploadReport(
        image: Bitmap,
        user: User,
        latitude: Double,
        longitude: Double,
        weatherCondition: String,
        onSuccess: (DocumentReference, UserReport) -> Unit,
        onFailure: (Exception) -> Unit

    ){
        uploadImage(
            image,
            user.userID!!,
            user.email + latitude + longitude + LocalDateTime.now().toString(),
            { fileURL ->
                createUserReport(
                    user,
                    LocalDateTime.now().toString(),
                    latitude,
                    longitude,
                    fileURL,
                    weatherCondition,
                    onSuccess,
                    onFailure
                )
            },
            onFailure
        )
    }

    /**
     * Retrieves all user reports from the database.
     * @param callback A lambda expression that receives the [QuerySnapshot] result.
     * @return void
     */
    fun getAllUserReports(callback: (ArrayList<UserReport>?) -> Unit
    ) {
        database.collection(UserReport.USER_REPORT_TABLE)
            .get()
            .addOnSuccessListener { documents ->
                val reports = ArrayList<UserReport>()
                for(document in documents){
                    reports.add(document.toObject<UserReport>())
                }
                callback(reports)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG_FIRESTORE, "Error getting documents.", exception)
                callback(null)
            }
    }

    /**
     * Gets the currently signed in user
     * @return The firebase user or null
     */
    fun getCurrentUser(): FirebaseUser?{
        return auth.currentUser
    }



    companion object{
        private val TAG_FIRESTORE = "FIRESTORE"
        private val TAG_FIREAUTH = "FIREAUTH"
        private val TAG_STORAGE = "STORAGE"
    }
}