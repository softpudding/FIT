// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SettingActivity$$ViewBinder<T extends com.example.fitmvp.view.activity.SettingActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230908, "field 'infoPhone'");
    target.infoPhone = finder.castView(view, 2131230908, "field 'infoPhone'");
    view = finder.findRequiredView(source, 2131230907, "field 'infoNickname'");
    target.infoNickname = finder.castView(view, 2131230907, "field 'infoNickname'");
    view = finder.findRequiredView(source, 2131230904, "field 'infoBirthday'");
    target.infoBirthday = finder.castView(view, 2131230904, "field 'infoBirthday'");
    view = finder.findRequiredView(source, 2131230905, "field 'infoGender'");
    target.infoGender = finder.castView(view, 2131230905, "field 'infoGender'");
    view = finder.findRequiredView(source, 2131230906, "field 'infoHeight'");
    target.infoHeight = finder.castView(view, 2131230906, "field 'infoHeight'");
    view = finder.findRequiredView(source, 2131230909, "field 'infoWeight'");
    target.infoWeight = finder.castView(view, 2131230909, "field 'infoWeight'");
    view = finder.findRequiredView(source, 2131230778, "field 'update'");
    target.update = finder.castView(view, 2131230778, "field 'update'");
    view = finder.findRequiredView(source, 2131231038, "field 'progress'");
    target.progress = finder.castView(view, 2131231038, "field 'progress'");
  }

  @Override public void unbind(T target) {
    target.infoPhone = null;
    target.infoNickname = null;
    target.infoBirthday = null;
    target.infoGender = null;
    target.infoHeight = null;
    target.infoWeight = null;
    target.update = null;
    target.progress = null;
  }
}
