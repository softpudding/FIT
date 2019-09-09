// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class NoticeDetailActivity$$ViewBinder<T extends com.example.fitmvp.view.activity.NoticeDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230980, "field 'titleView'");
    target.titleView = finder.castView(view, 2131230980, "field 'titleView'");
    view = finder.findRequiredView(source, 2131230979, "field 'timeView'");
    target.timeView = finder.castView(view, 2131230979, "field 'timeView'");
    view = finder.findRequiredView(source, 2131230978, "field 'msgView'");
    target.msgView = finder.castView(view, 2131230978, "field 'msgView'");
  }

  @Override public void unbind(T target) {
    target.titleView = null;
    target.timeView = null;
    target.msgView = null;
  }
}
