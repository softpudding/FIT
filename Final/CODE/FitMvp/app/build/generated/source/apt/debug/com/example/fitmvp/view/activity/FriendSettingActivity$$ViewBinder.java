// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FriendSettingActivity$$ViewBinder<T extends com.example.fitmvp.view.activity.FriendSettingActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230975, "field 'editNoteName'");
    target.editNoteName = finder.castView(view, 2131230975, "field 'editNoteName'");
    view = finder.findRequiredView(source, 2131231180, "field 'warning'");
    target.warning = finder.castView(view, 2131231180, "field 'warning'");
    view = finder.findRequiredView(source, 2131230771, "field 'edit'");
    target.edit = finder.castView(view, 2131230771, "field 'edit'");
  }

  @Override public void unbind(T target) {
    target.editNoteName = null;
    target.warning = null;
    target.edit = null;
  }
}
