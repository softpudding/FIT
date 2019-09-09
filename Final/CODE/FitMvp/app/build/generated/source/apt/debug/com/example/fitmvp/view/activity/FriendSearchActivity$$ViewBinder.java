// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FriendSearchActivity$$ViewBinder<T extends com.example.fitmvp.view.activity.FriendSearchActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131231048, "field 'search'");
    target.search = finder.castView(view, 2131231048, "field 'search'");
    view = finder.findRequiredView(source, 2131231057, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131231057, "field 'recyclerView'");
    view = finder.findRequiredView(source, 2131230919, "field 'inputPhone'");
    target.inputPhone = finder.castView(view, 2131230919, "field 'inputPhone'");
  }

  @Override public void unbind(T target) {
    target.search = null;
    target.recyclerView = null;
    target.inputPhone = null;
  }
}
