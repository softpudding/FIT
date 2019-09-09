// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends com.example.fitmvp.view.activity.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230911, "field 'inputAccount'");
    target.inputAccount = finder.castView(view, 2131230911, "field 'inputAccount'");
    view = finder.findRequiredView(source, 2131230914, "field 'inputPassword'");
    target.inputPassword = finder.castView(view, 2131230914, "field 'inputPassword'");
    view = finder.findRequiredView(source, 2131230772, "field 'login'");
    target.login = finder.castView(view, 2131230772, "field 'login'");
    view = finder.findRequiredView(source, 2131231194, "field 'buttonToRegister'");
    target.buttonToRegister = finder.castView(view, 2131231194, "field 'buttonToRegister'");
    view = finder.findRequiredView(source, 2131231195, "field 'changePassword'");
    target.changePassword = finder.castView(view, 2131231195, "field 'changePassword'");
    view = finder.findRequiredView(source, 2131231249, "field 'waiting'");
    target.waiting = finder.castView(view, 2131231249, "field 'waiting'");
  }

  @Override public void unbind(T target) {
    target.inputAccount = null;
    target.inputPassword = null;
    target.login = null;
    target.buttonToRegister = null;
    target.changePassword = null;
    target.waiting = null;
  }
}
