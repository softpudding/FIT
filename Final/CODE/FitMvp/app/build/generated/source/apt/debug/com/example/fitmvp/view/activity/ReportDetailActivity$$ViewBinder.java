// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ReportDetailActivity$$ViewBinder<T extends com.example.fitmvp.view.activity.ReportDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230803, "field 'beginView'");
    target.beginView = finder.castView(view, 2131230803, "field 'beginView'");
    view = finder.findRequiredView(source, 2131230804, "field 'endView'");
    target.endView = finder.castView(view, 2131230804, "field 'endView'");
    view = finder.findRequiredView(source, 2131231225, "field 'calVal'");
    target.calVal = finder.castView(view, 2131231225, "field 'calVal'");
    view = finder.findRequiredView(source, 2131231228, "field 'proVal'");
    target.proVal = finder.castView(view, 2131231228, "field 'proVal'");
    view = finder.findRequiredView(source, 2131231227, "field 'fatVal'");
    target.fatVal = finder.castView(view, 2131231227, "field 'fatVal'");
    view = finder.findRequiredView(source, 2131231226, "field 'carbVal'");
    target.carbVal = finder.castView(view, 2131231226, "field 'carbVal'");
    view = finder.findRequiredView(source, 2131231027, "field 'calStandard'");
    target.calStandard = finder.castView(view, 2131231027, "field 'calStandard'");
    view = finder.findRequiredView(source, 2131231030, "field 'proStandard'");
    target.proStandard = finder.castView(view, 2131231030, "field 'proStandard'");
    view = finder.findRequiredView(source, 2131231029, "field 'fatStandard'");
    target.fatStandard = finder.castView(view, 2131231029, "field 'fatStandard'");
    view = finder.findRequiredView(source, 2131231028, "field 'carbStandard'");
    target.carbStandard = finder.castView(view, 2131231028, "field 'carbStandard'");
  }

  @Override public void unbind(T target) {
    target.beginView = null;
    target.endView = null;
    target.calVal = null;
    target.proVal = null;
    target.fatVal = null;
    target.carbVal = null;
    target.calStandard = null;
    target.proStandard = null;
    target.fatStandard = null;
    target.carbStandard = null;
  }
}
