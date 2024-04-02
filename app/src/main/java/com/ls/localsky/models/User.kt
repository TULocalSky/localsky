package com.ls.localsky.models

class User(
    val userID: String,
    val firstName: String,
    val lastName: String,
    val email: String
) {

    companion object{
        public val USER_TABLE = "Users"
        public val USERID = "UserID"
        public val FIRST_NAME = "FirstName"
        public val LAST_NAME = "LastName"
        public val EMAIL_ADDRESS = "Email"
    }
}