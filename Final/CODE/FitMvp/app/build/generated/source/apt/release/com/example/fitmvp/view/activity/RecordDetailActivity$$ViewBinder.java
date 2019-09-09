// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RecordDetailActivity$$ViewBinder<T extends com.example.fitmvp.view.activity.RecordDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230820, "field 'titleView'");
    target.titleView = finder.castView(view, 2131230820, "field 'titleView'");
    view = finder.findRequiredView(source, 2131230819, "field 'timeView'");
    target.timeView = finder.castView(view, 2131230819, "field 'timeView'");
    view = finder.findRequiredView(source, 2131230821, "field 'weightView'");
    target.weightView = finder.castView(view, 2131230821, "field 'weightView'");
    view = finder.findRequiredView(source, 2131230815, "field 'calView'");
    target.calView = finder.castView(view, 2131230815, "field 'calView'");
    view = finder.findRequiredView(source, 2131230818, "field 'proView'");
    target.proView = finder.castView(view, 2131230818, "field 'proView'");
    view = finder.findRequiredView(source, 2131230817, "field 'fatView'");
    target.fatView = finder.castView(view, 2131230817, "field 'fatView'");
    view = finder.findRequiredView(source, 2131230816, "field 'carbView'");
    target.carbView = finder.castView(view, 2131230816, "field 'carbView'");
  }

  @Override public void unbind(T target) {
    target.titleView = null;
    target.timeView = null;
    target.weightView = null;
    target.calView = null;
    target.proView = null;
    target.fatView = null;
    target.carbView = null;
  }
}
