// Generated code from Butter Knife. Do not modify!
package com.example.fitmvp.chat.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ChatActivity$$ViewBinder<T extends com.example.fitmvp.chat.activity.ChatActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230949, "field 'lvChat'");
    target.lvChat = finder.castView(view, 2131230949, "field 'lvChat'");
    view = finder.findRequiredView(source, 2131230826, "field 'ekBar'");
    target.ekBar = finder.castView(view, 2131230826, "field 'ekBar'");
  }

  @Override public void unbind(T target) {
    target.lvChat = null;
    target.ekBar = null;
  }
}
