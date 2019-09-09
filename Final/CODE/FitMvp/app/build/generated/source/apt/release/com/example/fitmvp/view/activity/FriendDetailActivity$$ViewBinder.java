// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.view.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FriendDetailActivity$$ViewBinder<T extends com.example.fitmvp.view.activity.FriendDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230860, "field 'photo'");
    target.photo = finder.castView(view, 2131230860, "field 'photo'");
    view = finder.findRequiredView(source, 2131230976, "field 'noteOrNick'");
    target.noteOrNick = finder.castView(view, 2131230976, "field 'noteOrNick'");
    view = finder.findRequiredView(source, 2131230858, "field 'show_notename'");
    target.show_notename = finder.castView(view, 2131230858, "field 'show_notename'");
    view = finder.findRequiredView(source, 2131230859, "field 'show_phone'");
    target.show_phone = finder.castView(view, 2131230859, "field 'show_phone'");
    view = finder.findRequiredView(source, 2131230857, "field 'show_nickname'");
    target.show_nickname = finder.castView(view, 2131230857, "field 'show_nickname'");
    view = finder.findRequiredView(source, 2131230856, "field 'show_gender'");
    target.show_gender = finder.castView(view, 2131230856, "field 'show_gender'");
    view = finder.findRequiredView(source, 2131230855, "field 'show_birthday'");
    target.show_birthday = finder.castView(view, 2131230855, "field 'show_birthday'");
    view = finder.findRequiredView(source, 2131230767, "field 'action'");
    target.action = finder.castView(view, 2131230767, "field 'action'");
    view = finder.findRequiredView(source, 2131230768, "field 'linearLayout'");
    target.linearLayout = finder.castView(view, 2131230768, "field 'linearLayout'");
    view = finder.findRequiredView(source, 2131230769, "field 'agree'");
    target.agree = finder.castView(view, 2131230769, "field 'agree'");
    view = finder.findRequiredView(source, 2131230774, "field 'refuse'");
    target.refuse = finder.castView(view, 2131230774, "field 'refuse'");
  }

  @Override public void unbind(T target) {
    target.photo = null;
    target.noteOrNick = null;
    target.show_notename = null;
    target.show_phone = null;
    target.show_nickname = null;
    target.show_gender = null;
    target.show_birthday = null;
    target.action = null;
    target.linearLayout = null;
    target.agree = null;
    target.refuse = null;
  }
}
