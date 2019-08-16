package com.example.fitmvp.network;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpTest {
    private Http http;
    @Test
    public void testSingleton(){
        http = new Http();
        Http http2 = new Http();
        assertEquals(http.getInstance(),http2.getInstance());
    }
}