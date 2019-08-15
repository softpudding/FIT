package com.example.fitmvp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SpUtilsTest {
    private SpUtils spUtils;
    @Before
    public void setUp() throws Exception {
        spUtils = new SpUtils(SharePreferenceHelper.newInstance());
    }

    @Test
    public void putAndGet() {
        // before put
        String value = (String)spUtils.get("testString","");
        assertEquals("",value);
        // after put
        spUtils.put("testString","test");
        value = (String)spUtils.get("testString","");
        assertEquals("test",value);

        Integer valueI;
        // before put
        valueI = (Integer)spUtils.get("testInteger",0);
        assertEquals((Integer)0,valueI);
        // after put
        spUtils.put("testInteger", 1);
        valueI = (Integer)spUtils.get("testInteger",0);
        assertEquals((Integer)1,valueI);

        Boolean valueB;
        // before put
        valueB = (Boolean)spUtils.get("testBoolean",false);
        assertFalse(valueB);
        // after put
        spUtils.put("testBoolean",true);
        valueB = (Boolean)spUtils.get("testBoolean",false);
        assertTrue(valueB);

        Float valueF;
        // before put
        valueF = (Float)spUtils.get("testFloat",(float)0.1);
        assertEquals(Float.valueOf("0.1"),valueF);
        //after put
        spUtils.put("testFloat", (float)12.345);
        valueF = (Float)spUtils.get("testFloat",(float)0.1);
        assertEquals(Float.valueOf("12.345"),valueF);

        Long valueL;
        // before put
        valueL = (Long)spUtils.get("testLong",0L);
        assertEquals((Long)0L, valueL);
        // after put
        spUtils.put("testLong",450L);
        valueL = (Long)spUtils.get("testLong",0L);
        assertEquals((Long)450L, valueL);

        Object valueO = spUtils.get("testObj",spUtils);
        assertNull(valueO);
    }

    @Test
    public void remove() {
        spUtils.put("testRemove","test...");
        String value = (String)spUtils.get("testRemove","");
        assertEquals("test...",value);

        spUtils.remove("testRemove");

        value = (String)spUtils.get("testRemove","");
        assertEquals("",value);
    }

    @Test
    public void clear() {
        spUtils.put("testClear","clear");
        spUtils.put("testClear1",1);
        String value = (String)spUtils.get("testClear","");
        Integer valueI = (Integer)spUtils.get("testClear1",0);
        assertEquals("clear",value);
        assertEquals((Integer)1, valueI);

        spUtils.clear();

        value = (String)spUtils.get("testClear","");
        valueI = (Integer)spUtils.get("testClear1",0);
        assertEquals("",value);
        assertEquals((Integer)0, valueI);
    }

    @Test
    public void setCachedNewFriendNum() {
        String key = "CachedNewFriend";
        Integer num = (Integer)spUtils.get(key,0);
        assertEquals((Integer)0, num);

        spUtils.setCachedNewFriendNum(20);
        num = (Integer)spUtils.get(key,0);
        assertEquals((Integer)20, num);
    }

    @Test
    public void getCachedNewFriendNum() {
        String key = "CachedNewFriend";
        Integer num = spUtils.getCachedNewFriendNum();
        assertEquals((Integer)0,num);

        spUtils.put(key,30);
        num = spUtils.getCachedNewFriendNum();
        assertEquals((Integer)30,num);
    }
}