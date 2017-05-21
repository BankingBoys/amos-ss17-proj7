package de.fau.amos.virtualledger.android;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.fau.amos.virtualledger.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterLoginTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void registerAndLogin() throws InterruptedException {
        onView(withId(R.id.textViewLogin_RegisterFirst)).perform(click());
        onView(withId(R.id.RegistrationView)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.terms_and_conditions)).check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.FirstName)).perform(typeText("Test")).perform(closeSoftKeyboard());
        onView(withId(R.id.LastName)).perform(typeText("User")).perform(closeSoftKeyboard());
        onView(withId(R.id.Email)).perform(typeText("test@user.com")).perform(closeSoftKeyboard());
        onView(withId(R.id.Password)).perform(typeText("testpassword")).perform(closeSoftKeyboard());
        onView(withId(R.id.button_register)).perform(click());
        onView(withId(R.id.userIDField)).perform(typeText("test@user.com")).perform(closeSoftKeyboard());
        onView(withId(R.id.SecretField)).perform(typeText("testpassword")).perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
    }
}
