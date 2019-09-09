// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FriendRecommendActivity$$ViewBinder<T extends com.example.fitmvp.view.activity.FriendRecommendActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131231017, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131231017, "field 'recyclerView'");
    view = finder.findRequiredView(source, 2131231016, "field 'hint'");
    target.hint = finder.castView(view, 2131231016, "field 'hint'");
    view = finder.findRequiredView(source, 2131231018, "field 'mView'");
    target.mView = finder.castView(view, 2131231018, "field 'mView'");
  }

  @Override public void unbind(T target) {
    target.recyclerView = null;
    target.hint = null;
    target.mView = null;
  }
}
