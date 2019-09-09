// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FriendAddActivity$$ViewBinder<T extends com.example.fitmvp.view.activity.FriendAddActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131231062, "field 'sendReason'");
    target.sendReason = finder.castView(view, 2131231062, "field 'sendReason'");
    view = finder.findRequiredView(source, 2131230918, "field 'inputReason'");
    target.inputReason = finder.castView(view, 2131230918, "field 'inputReason'");
  }

  @Override public void unbind(T target) {
    target.sendReason = null;
    target.inputReason = null;
  }
}
