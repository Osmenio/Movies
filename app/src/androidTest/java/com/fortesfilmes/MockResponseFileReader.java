package com.fortesfilmes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MockResponseFileReader {

    private static StringBuffer content = new StringBuffer();

    public MockResponseFileReader(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(path)));
        String str;
        while ((str = reader.readLine()) != null) {
            content.append(str);
        }
        System.out.println(content.toString());
    }

    public static String getContent() {
        return content.toString();
    }
}
