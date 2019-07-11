package com.example.fitmvp.view.activity;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.fitmvp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityTestRule mActivity = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testLogin(){
        // 清空输入
        onView(withId(R.id.input_phone)).perform(clearText());
        onView(withId(R.id.input_password)).perform(clearText());
        // 点击登录按钮
        onView(withId(R.id.button_login)).perform(click()).check(matches(isEnabled()));
        // 判断账号和密码输入栏为空
        onView(withId(R.id.input_phone)).check(matches(isDisplayed()))
                .check(matches(withText("")));
        onView(withId(R.id.input_password)).check(matches(isDisplayed()))
                .check(matches(withText("")));

        // 输入账号100,然后关闭键盘
        onView(withId(R.id.input_phone))
                .perform(typeText("100\n"), closeSoftKeyboard());
        // 点击登录按钮
        onView(withId(R.id.button_login)).perform(click());
        // 判断账号输入栏为“100”，密码输入栏为空
        onView(withId(R.id.input_phone)).check(matches(isDisplayed()))
                .check(matches(withText("100")));
        onView(withId(R.id.input_password)).check(matches(isDisplayed()))
                .check(matches(withText("")));

        // 输入密码“abcde”,然后关闭键盘
        onView(withId(R.id.input_password))
                .perform(typeText("abcde\n"), closeSoftKeyboard())
                .check(matches(withText("abcde")));
        // 点击登录按钮
        onView(withId(R.id.button_login)).perform(click());
        // 账号不存在
        onView(withText("账号不存在")).check(matches(isDisplayed()));
        onView(withText("是")).check(matches(isEnabled())).perform(click());

        // 清空账号，输入“101”
        onView(withId(R.id.input_phone)).perform(clearText())
                .perform(typeText("101\n"), closeSoftKeyboard());
        // 点击登录按钮
        onView(withId(R.id.button_login)).perform(click());
        // 密码错误
        onView(withText("密码不正确")).check(matches(isDisplayed()));
        onView(withText("是")).check(matches(isEnabled())).perform(click());

        // 清空账号，输入“104”
        onView(withId(R.id.input_phone)).perform(clearText())
                .perform(typeText("104\n"), closeSoftKeyboard());
        // 清空密码，输入“d”
        onView(withId(R.id.input_password)).perform(clearText())
                .perform(typeText("d\n"), closeSoftKeyboard());
        // 点击登录按钮
        onView(withId(R.id.button_login)).perform(click());
        // 账号被禁用
        onView(withText("账号被禁用")).check(matches(isDisplayed()));
        onView(withText("是")).check(matches(isEnabled())).perform(click());
    }
}