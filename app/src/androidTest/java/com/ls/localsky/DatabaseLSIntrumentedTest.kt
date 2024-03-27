package com.ls.localsky

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class DatabaseLSIntrumentedTest {

    //Testing is not working
    @Test
    fun firebaseCreateUser(){
        val database = DatabaseLS()

        database.createUser(
            "TestFirstName",
            "TestLastName",
            "test@test.com",
            "12345",
            { FirebaseUser, User ->
                assert(User.firstName == "TestFirstName")
            },
            {

            }
        )

    }

    @Test
    fun firebaseGetUserTable(){
        val database = DatabaseLS()

        database
    }
}