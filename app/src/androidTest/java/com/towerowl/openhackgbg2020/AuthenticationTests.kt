package com.towerowl.openhackgbg2020

import androidx.room.Room
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.towerowl.openhackgbg2020.data.AppDatabase
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AuthenticationTests {

    private fun memoryDatabase() = Room.inMemoryDatabaseBuilder(
        tContext(),
        AppDatabase::class.java
    )

    private fun tContext() = InstrumentationRegistry.getInstrumentation().targetContext

    fun authenticationEndToEnd() {
        onView(withId(R.id.authentication_cancel))
    }

}