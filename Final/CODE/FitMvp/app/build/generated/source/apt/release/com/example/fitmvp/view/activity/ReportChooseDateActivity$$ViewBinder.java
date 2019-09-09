// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ReportChooseDateActivity$$ViewBinder<T extends com.example.fitmvp.view.activity.ReportChooseDateActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131231104, "field 'startDate'");
    target.startDate = finder.castView(view, 2131231104, "field 'startDate'");
    view = finder.findRequiredView(source, 2131230830, "field 'endDate'");
    target.endDate = finder.castView(view, 2131230830, "field 'endDate'");
    view = finder.findRequiredView(source, 2131231063, "field 'setDate'");
    target.setDate = finder.castView(view, 2131231063, "field 'setDate'");
    view = finder.findRequiredView(source, 2131230870, "field 'getReport'");
    target.getReport = finder.castView(view, 2131230870, "field 'getReport'");
  }

  @Override public void unbind(T target) {
    target.startDate = null;
    target.endDate = null;
    target.setDate = null;
    target.getReport = null;
  }
}
