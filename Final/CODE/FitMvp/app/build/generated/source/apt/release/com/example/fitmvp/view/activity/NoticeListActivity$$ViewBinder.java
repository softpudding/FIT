// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class NoticeListActivity$$ViewBinder<T extends com.example.fitmvp.view.activity.NoticeListActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230981, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131230981, "field 'recyclerView'");
    view = finder.findRequiredView(source, 2131230982, "field 'progress'");
    target.progress = finder.castView(view, 2131230982, "field 'progress'");
  }

  @Override public void unbind(T target) {
    target.recyclerView = null;
    target.progress = null;
  }
}
