// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisterActivity$$ViewBinder<T extends com.example.fitmvp.view.activity.RegisterActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230775, "field 'register'");
    target.register = finder.castView(view, 2131230775, "field 'register'");
    view = finder.findRequiredView(source, 2131230915, "field 'inputPhone'");
    target.inputPhone = finder.castView(view, 2131230915, "field 'inputPhone'");
    view = finder.findRequiredView(source, 2131230913, "field 'inputName'");
    target.inputName = finder.castView(view, 2131230913, "field 'inputName'");
    view = finder.findRequiredView(source, 2131230916, "field 'inputPwd'");
    target.inputPwd = finder.castView(view, 2131230916, "field 'inputPwd'");
    view = finder.findRequiredView(source, 2131230917, "field 'inputPwdAgain'");
    target.inputPwdAgain = finder.castView(view, 2131230917, "field 'inputPwdAgain'");
    view = finder.findRequiredView(source, 2131230912, "field 'inputMsg'");
    target.inputMsg = finder.castView(view, 2131230912, "field 'inputMsg'");
    view = finder.findRequiredView(source, 2131230869, "field 'getMsg'");
    target.getMsg = finder.castView(view, 2131230869, "field 'getMsg'");
  }

  @Override public void unbind(T target) {
    target.register = null;
    target.inputPhone = null;
    target.inputName = null;
    target.inputPwd = null;
    target.inputPwdAgain = null;
    target.inputMsg = null;
    target.getMsg = null;
  }
}
