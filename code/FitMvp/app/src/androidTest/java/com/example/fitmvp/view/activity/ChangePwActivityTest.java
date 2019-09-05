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
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ChangePwActivityTest {
    @Rule
    public ActivityTestRule mActivity = new ActivityTestRule<>(ChangePwActivity.class);

    @Test
    public void testInputCheck(){
        // 清空输入
        onView(withId(R.id.change_phone)).perform(clearText())
                .check(matches(withText("")));
        onView(withId(R.id.change_pwd)).perform(clearText())
                .check(matches(withText("")));
        onView(withId(R.id.change_pwd_again)).perform(clearText())
                .check(matches(withText("")));
        // 点击确认修改按钮
        onView(withId(R.id.button_change)).perform(click()).check(matches(isEnabled()));
        // 判断手机号输入栏为空
        onView(withId(R.id.change_phone)).check(matches(hasErrorText("手机号不能为空")));

        // 输入手机号 “12345678”
        onView(withId(R.id.change_phone))
                .perform(typeText("?12345678a\n"), closeSoftKeyboard())
                .check(matches(withText("12345678")));
        // 点击确认修改按钮
        onView(withId(R.id.button_change)).perform(click());
        // 判断用户名为空
        onView(withId(R.id.change_pwd)).check(matches(withHint("请输入密码")));

        // 输入密码
        onView(withId(R.id.change_pwd))
                .perform(typeText("password\n"), closeSoftKeyboard());
        // 点击确认修改按钮
        onView(withId(R.id.button_change)).perform(click());
        // 判断没有确认密码
        onView(withId(R.id.change_pwd_again)).check(matches(hasErrorText("请确认密码")));

        // 确认密码，但输入与第一次不同
        onView(withId(R.id.change_pwd_again))
                .perform(typeText("notSamePassword\n"), closeSoftKeyboard());
        // 点击确认修改按钮
        onView(withId(R.id.button_change)).perform(click());
        // 判断密码不一致
        onView(withId(R.id.change_pwd_again)).check(matches(hasErrorText("两次密码不一致，请确认密码")));
    }

    @Test
    public void testMessageFail(){
        // 输入
        // phone "01234567890"
        onView(withId(R.id.change_phone)).perform(clearText())
                .perform(typeText("01234567890\n"), closeSoftKeyboard())
                .check(matches(withText("01234567890")));
        // password "password"
        onView(withId(R.id.change_pwd)).perform(clearText())
                .perform(typeText("password\n"), closeSoftKeyboard())
                .check(matches(withText("password")));
        // password again "password"
        onView(withId(R.id.change_pwd_again)).perform(clearText())
                .perform(typeText("password\n"), closeSoftKeyboard())
                .check(matches(withText("password")));
        // 点击确认修改按钮
        onView(withId(R.id.button_change)).perform(click());
        onView(withId(R.id.input_msg_c)).check(matches(hasErrorText("请输入验证码")));
    }
}