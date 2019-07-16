package com.example.fitmvp.exception;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ExceptionEngineTest {
    private int code;
    private int expectedCode;
    private String msg;
    private String expectedMsg;

    @Parameterized.Parameters
    public static Collection prepareData(){
        Object [][] object = {
                {0,1003,"something wrong","userName or passWord is error!"},
                {401,1003,"something wrong","当前请求需要用户验证"},
                {403,1003,"something wrong","服务器已经理解请求，但是拒绝执行它"},
                {404,1003,"something wrong","服务器异常，请稍后再试"},
                {408,1003,"something wrong","请求超时"},
                {500,1003,"something wrong","服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理"},
                {502,1003,"something wrong","作为网关或者代理工作的服务器尝试执行请求时，从上游服务器接收到无效的响应"},
                {503,1003,"something wrong","由于临时的服务器维护或者过载，服务器当前无法处理请求"},
                {504,1003,"something wrong","作为网关或者代理工作的服务器尝试执行请求时，未能及时从上游服务器"+
                                            "（URI标识出的服务器，例如HTTP、FTP、LDAP）或者辅助服务器（例如DNS）收到响应"},
                {501,1003,"something wrong","网络错误"},
                // 自定义code，用来在测试中分别类型
                {600,1001,"something wrong","解析错误"},
                {601,1002,"something wrong","服务器连接失败，请检查网络"},
                {602,1002,"something wrong","服务器连接失败，请检查网络"},
                {603,1000,"something wrong","未知错误"},
        };
        return Arrays.asList(object);
    }

    public ExceptionEngineTest(int code, int expectedCode, String msg, String expectedMsg){
        this.code = code;
        this.expectedCode = expectedCode;
        this.msg = msg;
        this.expectedMsg = expectedMsg;
    }

    @Test
    public void testHandleException(){
        ApiException returnException;
        if(code <= 504){
            ServerException e = new ServerException(code,msg);
            returnException = ExceptionEngine.handleException(e);

        }
        else if(code == 600){
            Throwable throwable = new Throwable();
            JSONException e = new JSONException(msg,throwable);
            returnException = ExceptionEngine.handleException(e);
        }
        else if(code == 601){
            ConnectException e = new ConnectException();
            returnException = ExceptionEngine.handleException(e);
        }
        else if(code == 602){
            SocketTimeoutException e = new SocketTimeoutException();
            returnException = ExceptionEngine.handleException(e);
        }
        else{
            Throwable e = new Throwable();
            returnException = ExceptionEngine.handleException(e);
        }
        assertEquals(expectedCode,returnException.code);
        assertEquals(expectedMsg,returnException.message);
    }

}