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
public class LoginActivityTest {
    @Rule
    public ActivityTestRule mActivity = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testInputCheck(){
        // 清空输入
        onView(withId(R.id.input_account)).perform(clearText())
                .check(matches(withText("")));
        onView(withId(R.id.input_password)).perform(clearText())
                .check(matches(withText("")));
        // 点击登录按钮
        onView(withId(R.id.button_login)).perform(click()).check(matches(isEnabled()));
        // 判断账号输入栏为空
        onView(withId(R.id.input_account)).check(matches(hasErrorText("账号不能为空")));

        // 输入账号100,且只能输入数字,然后关闭键盘
        onView(withId(R.id.input_account))
                .perform(typeText(" +100a\n"), closeSoftKeyboard())
                .check(matches(withText("100")));
        // 点击登录按钮
        onView(withId(R.id.button_login)).perform(click());
        // 判断密码输入栏为空
        onView(withId(R.id.input_password)).check(matches(withHint("密码")));
    }

    @Test
    public void testAccountNotExsist(){
        // 账号不存在， 账号“777”
        onView(withId(R.id.input_account)).perform(clearText())
                .perform(typeText("777\n"), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(clearText())
                .perform(typeText("abcde\n"), closeSoftKeyboard())
                .check(matches(withText("abcde")));
        // 点击登录按钮
        onView(withId(R.id.button_login)).perform(click());
        // 账号不存在
        onView(withText("账号不存在")).check(matches(isDisplayed()));
        onView(withText("是")).check(matches(isEnabled())).perform(click());
    }

    @Test
    public void testPwdWrong(){
        // 密码错误
        onView(withId(R.id.input_account)).perform(clearText())
                .perform(typeText("15022265465\n"), closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(clearText())
                .perform(typeText("abcde\n"), closeSoftKeyboard())
                .check(matches(withText("abcde")));
        // 点击登录按钮
        onView(withId(R.id.button_login)).perform(click());
        // 密码错误
        onView(withText("密码不正确")).check(matches(isDisplayed()));
        onView(withText("是")).check(matches(isEnabled())).perform(click());
    }

//    @Test
//    public void testBannedUser(){
//        // 清空账号，输入“104” - 被禁用账号
//        onView(withId(R.id.input_account)).perform(clearText())
//                .perform(typeText("104\n"), closeSoftKeyboard());
//        // 清空密码，输入“d”
//        onView(withId(R.id.input_password)).perform(clearText())
//                .perform(typeText("d\n"), closeSoftKeyboard());
//        // 点击登录按钮
//        onView(withId(R.id.button_login)).perform(click());
//        // 账号被禁用
//        onView(withText("账号被禁用")).check(matches(isDisplayed()));
//        onView(withText("是")).check(matches(isEnabled())).perform(click());
//    }

    @Test
    public void testLoginSuccess(){
        // 为测试登录成功的情况
        // 清空账号，输入“102”
        onView(withId(R.id.input_account)).perform(clearText())
                .perform(typeText("15022265465\n"), closeSoftKeyboard());
        // 清空密码，输入“b”
        onView(withId(R.id.input_password)).perform(clearText())
                .perform(typeText("123456\n"), closeSoftKeyboard());
        // 点击登录按钮
        onView(withId(R.id.button_login)).perform(click());
        // 登录成功
    }

    @Test
    public void testUI(){
        // 检查去注册页面的button
        onView(withId(R.id.toRegister))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));
        // 检查忘记密码的button
        onView(withId(R.id.toRepassword))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));
        // 检查logo
        onView(withId(R.id.loginIcon))
                .check(matches(isDisplayed()));
    }
}