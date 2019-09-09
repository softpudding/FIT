// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WelcomeActivity$$ViewBinder<T extends com.example.fitmvp.view.activity.WelcomeActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230934, "field 'jump'");
    target.jump = finder.castView(view, 2131230934, "field 'jump'");
  }

  @Override public void unbind(T target) {
    target.jump = null;
  }
}
