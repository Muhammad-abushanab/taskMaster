package com.shanab.taskmaster;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import com.shanab.taskmaster.Activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void mainActivityLayoutTest(){
        Espresso.onView(ViewMatchers.withId(R.id.homeHeader))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(ViewMatchers.withText("No nickname")));

        Espresso.onView(ViewMatchers.withId(R.id.addTask))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.allTasks))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.settingsButton))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.TaskRecView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }
    @Test
    public void checkButtonsWithClickAbility(){
        Espresso.onView(ViewMatchers.withId(R.id.allTasks))
                .check(ViewAssertions.matches(ViewMatchers.withText("All Tasks")))
                .perform(ViewActions.click());
        Espresso.pressBack();
        Espresso.onView(ViewMatchers.withId(R.id.addTask))
                .check(ViewAssertions.matches(ViewMatchers.withText("Add Task")))
                .perform(ViewActions.click());
        Espresso.pressBack();
        Espresso.onView(ViewMatchers.withId(R.id.settingsButton))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());
    }
}
