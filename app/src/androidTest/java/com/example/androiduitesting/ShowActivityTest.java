package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowActivityTest {
    
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp() {
        // Initialize Intents before each test
        Intents.init();
    }

    @After
    public void tearDown() {
        // Release Intents after each test
        Intents.release();
    }

    /**
     * Test Case 1: Check whether the activity correctly switched
     * This test verifies that clicking on a city item launches ShowActivity
     */
    @Test
    public void testActivitySwitch() {
        // Add a city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on the city in the list
        onData(anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Verify that ShowActivity was launched with the correct intent
        Intents.intended(IntentMatchers.hasComponent(ShowActivity.class.getName()));
    }

    /**
     * Test Case 2: Test whether the city name is consistent
     * This test verifies that the city name displayed in ShowActivity 
     * matches the city name clicked in MainActivity
     */
    @Test
    public void testCityNameConsistency() {
        String cityName = "Vancouver";
        
        // Add a city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText(cityName));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on the city in the list
        onData(anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Verify that the city name is displayed correctly in ShowActivity
        onView(withId(R.id.textView_cityName)).check(matches(withText(cityName)));
        onView(withId(R.id.textView_cityName)).check(matches(isDisplayed()));
    }

    /**
     * Test Case 3: Test the "back" button
     * This test verifies that clicking the back button in ShowActivity
     * returns the user to MainActivity
     */
    @Test
    public void testBackButton() {
        // Add a city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Toronto"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on the city in the list to open ShowActivity
        onData(anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Verify we're in ShowActivity
        onView(withId(R.id.button_back)).check(matches(isDisplayed()));

        // Click the back button
        onView(withId(R.id.button_back)).perform(click());

        // Verify we're back in MainActivity by checking if the add button is visible
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
    }
}
