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

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {
    @Rule
    public ActivityTestRule mActivity = new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void testInputCheck(){
        // 清空输入
        onView(withId(R.id.input_phone)).perform(clearText())
                .check(matches(withText("")));
        onView(withId(R.id.input_name)).perform(clearText())
                .check(matches(withText("")));
        onView(withId(R.id.input_pwd)).perform(clearText())
                .check(matches(withText("")));
        onView(withId(R.id.input_pwd_again)).perform(clearText())
                .check(matches(withText("")));
        // 点击注册按钮
        onView(withId(R.id.button_register)).perform(click()).check(matches(isEnabled()));
        // 判断手机号输入栏为空
        onView(withId(R.id.input_phone)).check(matches(hasErrorText("手机号不能为空")));

        // 输入账号“101”
        onView(withId(R.id.input_phone))
                .perform(typeText("?101a  \n"), closeSoftKeyboard())
                .check(matches(withText("101")));
        // 点击登录按钮
        onView(withId(R.id.button_register)).perform(click());
        // 判断用户名为空
        onView(withId(R.id.input_name)).check(matches(withHint("请输入用户名")));

        // 输入用户名
        onView(withId(R.id.input_name))
                .perform(typeText("test\n"), closeSoftKeyboard())
                .check(matches(withText("test")));
        // 点击登录按钮
        onView(withId(R.id.button_register)).perform(click());
        // 判断密码为空
        onView(withId(R.id.input_pwd)).check(matches(hasErrorText("密码不能为空")));

        // 输入密码
        onView(withId(R.id.input_pwd))
                .perform(typeText("password\n"), closeSoftKeyboard());
        // 点击登录按钮
        onView(withId(R.id.button_register)).perform(click());
        // 判断没有确认密码
        onView(withId(R.id.input_pwd_again)).check(matches(hasErrorText("请确认密码")));

        // 确认密码，但输入与第一次不同
        onView(withId(R.id.input_pwd_again))
                .perform(typeText("notSamePassword\n"), closeSoftKeyboard());
        // 点击登录按钮
        onView(withId(R.id.button_register)).perform(click());
        // 判断密码不一致
        onView(withId(R.id.input_pwd_again)).check(matches(hasErrorText("两次密码不一致，请确认密码")));
    }

    @Test
    public void testRegisterFail(){
        // 输入
        // phone "101"
        onView(withId(R.id.input_phone)).perform(clearText())
                .perform(typeText("101\n"), closeSoftKeyboard())
                .check(matches(withText("101")));
        // name "test"
        onView(withId(R.id.input_name)).perform(clearText())
                .perform(typeText("test\n"), closeSoftKeyboard())
                .check(matches(withText("test")));
        // password "password"
        onView(withId(R.id.input_pwd)).perform(clearText())
                .perform(typeText("password\n"), closeSoftKeyboard())
                .check(matches(withText("password")));
        // password again "password"
        onView(withId(R.id.input_pwd_again)).perform(clearText())
                .perform(typeText("password\n"), closeSoftKeyboard())
                .check(matches(withText("password")));
        // 点击登录按钮
        onView(withId(R.id.button_register)).perform(click());
        // 未输入验证码
        onView(withId(R.id.input_msg)).check(matches(hasErrorText("请输入验证码")));

    }

}