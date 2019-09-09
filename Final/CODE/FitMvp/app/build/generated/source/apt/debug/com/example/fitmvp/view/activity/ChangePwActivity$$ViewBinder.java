// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ChangePwActivity$$ViewBinder<T extends com.example.fitmvp.view.activity.ChangePwActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230788, "field 'inputPhone'");
    target.inputPhone = finder.castView(view, 2131230788, "field 'inputPhone'");
    view = finder.findRequiredView(source, 2131230789, "field 'changePw'");
    target.changePw = finder.castView(view, 2131230789, "field 'changePw'");
    view = finder.findRequiredView(source, 2131230790, "field 'pwAgain'");
    target.pwAgain = finder.castView(view, 2131230790, "field 'pwAgain'");
    view = finder.findRequiredView(source, 2131230770, "field 'change'");
    target.change = finder.castView(view, 2131230770, "field 'change'");
  }

  @Override public void unbind(T target) {
    target.inputPhone = null;
    target.changePw = null;
    target.pwAgain = null;
    target.change = null;
  }
}
