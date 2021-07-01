package com.fortesfilmes;

import android.util.Log;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private Random random = new Random();

    @Test
    public void addition_isCorrect() {
//        for (int i = 0; i < 10; i++) {
//            float rating = random.nextFloat() * 5;
//            System.out.println("ExampleUnitTest: " + rating);
//        }

        assertEquals(4, 2 + 2);
    }
}