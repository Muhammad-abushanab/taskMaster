package com.shanab.taskmaster;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
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
public class AddTaskScenarioTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void addTaskTest(){
        Espresso.onView(ViewMatchers.withId(R.id.addTask))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.tasktitle))
                .perform(ViewActions.typeText("Simple Test"));
        Espresso.onView(ViewMatchers.withId(R.id.taskDescription))
                .perform(ViewActions.typeText("This is an Espresso Test"));
        Espresso.onView(ViewMatchers.withId(R.id.AddTaskBtn))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());

    }
    @Test
    public void addTaskThenViewTheTask(){
        addTaskTest();
        Espresso.onView(ViewMatchers.withId(R.id.TaskRecView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,ViewActions.click()));
        Espresso.onView(ViewMatchers.withId(R.id.TaskDescription))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.deleteTask))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.TaskState))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    @Test
    public void deleteTaskAfterAdding(){
        addTaskThenViewTheTask();
        Espresso.onView(ViewMatchers.withId(R.id.deleteTask))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());
    }
}
