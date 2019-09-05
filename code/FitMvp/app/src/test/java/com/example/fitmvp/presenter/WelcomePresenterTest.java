package com.example.fitmvp.presenter;

import com.example.fitmvp.model.WelcomeModel;
import com.example.fitmvp.utils.SharePreferenceHelper;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.view.activity.WelcomeActivity;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class WelcomePresenterTest {

    @Test
    public void jump() {
        WelcomePresenter presenter = new WelcomePresenter();
        SpUtils spUtils = new SpUtils(SharePreferenceHelper.newInstance());
        WelcomeActivity welcomeActivity = mock(WelcomeActivity.class);
        presenter.jump(spUtils,welcomeActivity);
        verify(welcomeActivity).toLogin();
        spUtils.put("isLogin", true);
        presenter.jump(spUtils,welcomeActivity);
        verify(welcomeActivity).toMainPage();
    }
}