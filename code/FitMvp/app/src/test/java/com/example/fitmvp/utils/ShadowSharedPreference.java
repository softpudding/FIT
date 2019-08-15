package com.example.fitmvp.utils;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ShadowSharedPreference implements SharedPreferences {

    Editor editor;

    List<OnSharedPreferenceChangeListener> mOnChangeListeners = new ArrayList<>();
    Map<String, Object> map                = new ConcurrentHashMap<>();

    public ShadowSharedPreference() {
        editor = new ShadowEditor(new EditorCall() {

            @Override
            public void apply(Map<String, Object> commitMap, List<String> removeList, boolean commitClear) {
                Map<String, Object> realMap = map;

                // clear
                if (commitClear) {
                    realMap.clear();
                }

                // 移除元素
                for (String key : removeList) {
                    realMap.remove(key);

                    for (OnSharedPreferenceChangeListener listener : mOnChangeListeners) {
                        listener.onSharedPreferenceChanged(ShadowSharedPreference.this, key);
                    }
                }

                // 添加元素
                Set<String> keys = commitMap.keySet();

                // 对比前后变化
                for (String key : keys) {
                    Object lastValue = realMap.get(key);
                    Object value     = commitMap.get(key);

                    if ((lastValue == null && value != null) || (lastValue != null && value == null) || !lastValue.equals(value)) {
                        for (OnSharedPreferenceChangeListener listener : mOnChangeListeners) {
                            listener.onSharedPreferenceChanged(ShadowSharedPreference.this, key);
                        }
                    }
                }

                realMap.putAll(commitMap);
            }
        });
    }

    public Map<String, ?> getAll() {
        return new HashMap<>(map);
    }

    public String getString(String key, @Nullable String defValue) {
        if (map.containsKey(key)) {
            return (String) map.get(key);
        }

        return defValue;
    }

    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        if (map.containsKey(key)) {
            return new HashSet<>((Set<String>) map.get(key));
        }

        return defValues;
    }

    public int getInt(String key, int defValue) {
        if (map.containsKey(key)) {
            return (Integer) map.get(key);
        }

        return defValue;
    }

    public long getLong(String key, long defValue) {
        if (map.containsKey(key)) {
            return (Long) map.get(key);
        }

        return defValue;
    }

    public float getFloat(String key, float defValue) {
        if (map.containsKey(key)) {
            return (Float) map.get(key);
        }

        return defValue;
    }

    public boolean getBoolean(String key, boolean defValue) {
        if (map.containsKey(key)) {
            return (Boolean) map.get(key);
        }

        return defValue;
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }

    public Editor edit() {
        return editor;
    }

    /**
     * 监听对应的key值的变化，只有当key对应的value值发生变化时，才会触发
     *
     * @param listener
     */
    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mOnChangeListeners.add(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mOnChangeListeners.remove(listener);
    }

    interface EditorCall {
        void apply(Map<String, Object> map, List<String> removeList, boolean commitClear);
    }

    public class ShadowEditor implements SharedPreferences.Editor {

        boolean commitClear;

        Map<String, Object> map        = new ConcurrentHashMap<>();
        /**
         * 待移除列表
         */
        List<String>        removeList = new ArrayList<>();

        EditorCall mCall;

        public ShadowEditor(EditorCall call) {
            this.mCall = call;
        }

        public ShadowEditor putString(String key, @Nullable String value) {
            map.put(key, value);
            return this;
        }

        public ShadowEditor putStringSet(String key, @Nullable Set<String> values) {
            map.put(key, new HashSet<>(values));
            return this;
        }

        public ShadowEditor putInt(String key, int value) {
            map.put(key, value);
            return this;
        }

        public ShadowEditor putLong(String key, long value) {
            map.put(key, value);
            return this;
        }

        public ShadowEditor putFloat(String key, float value) {
            map.put(key, value);
            return this;
        }

        public ShadowEditor putBoolean(String key, boolean value) {
            map.put(key, value);
            return this;
        }

        public ShadowEditor remove(String key) {
            map.remove(key);
            removeList.add(key);
            return this;
        }

        public ShadowEditor clear() {
            commitClear = true;
            map.clear();
            removeList.clear();
            return this;
        }

        public boolean commit() {
            try {
                apply();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public void apply() {
            mCall.apply(map, removeList, commitClear);

            // 每次提交清空缓存数据
            map.clear();
            commitClear = false;
            removeList.clear();
        }
    }
}