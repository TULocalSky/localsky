package com.ls.localsky

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class DatabaseLSIntrumentedTest {

    @Test
    fun firebaseCreateUser(){
        val database = DatabaseLS()

        val user = User(
            "1",
            "TestFirstName",
            "TestLastName",
            "test@test.com"
        )

    }

    @Test
    fun firebaseGetUserTable(){
        val database = DatabaseLS()

        database
    }
}