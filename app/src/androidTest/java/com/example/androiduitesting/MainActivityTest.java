package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAddCity() {
        // Click the "ADD" button
        onView(withId(R.id.button_add)).perform(click());
        // Type "Edmonton" into the EditText
        onView(withId(R.id.editText_name)).perform(typeText("Edmonton"));
        // Click the "CONFIRM" button
        onView(withId(R.id.button_confirm)).perform(click());
        // Check if "Edmonton" is displayed in the list
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }

    @Test
    public void testClearCity() {
        // 1. Add a city first
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Calgary"));
        onView(withId(R.id.button_confirm)).perform(click());

        // 2. Click the "CLEAR" button
        onView(withId(R.id.button_clear)).perform(click());

        // 3. Check that the city no longer exists in the UI
        onView(withText("Calgary")).check(doesNotExist());
    }

    @Test
    public void testListView() {
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Regina"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Check if the city is in the list view at the first position
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .check(matches(withText("Regina")));
    }

    @Test
    public void testActivitySwitch() {
        // Add a city to click on
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click the first item in the list
        onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // Check if the view from the new activity is displayed
        onView(withId(R.id.textView_cityName)).check(matches(isDisplayed()));
    }

    @Test
    public void testCityNameConsistency() {
        // Add a specific city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Toronto"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on that city in the list
        onData(is("Toronto")).inAdapterView(withId(R.id.city_list)).perform(click());

        // Verify the text in the new activity matches
        onView(withId(R.id.textView_cityName)).check(matches(withText("Toronto")));
    }

    @Test
    public void testBackButton() {
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(typeText("Winnipeg"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Navigate to the second activity
        onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // Verify we are in the second activity
        onView(withId(R.id.textView_cityName)).check(matches(isDisplayed()));

        // Press the back button
        onView(withId(R.id.button_back)).perform(click());


        // Verify we are back in MainActivity
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
    }
}
