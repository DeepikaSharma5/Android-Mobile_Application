package com.example.myapplication1.IT19134468_Sithpavan;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.example.myapplication1.detailsfragment;

import com.example.myapplication1.CartFrag;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.*;

public class ExampleUnitTest {
    static CartFrag cartFrag;
    int[] tot_price1 = {10, 20, 30, 40, 50};
    int sum1 = 0;
    int[] tot_price2 = {3234, 12, 432, 12, 321};
    int sum2 = 0;
    int[] tot_price3 = {345, 200, 130, 409, 750};
    int sum3 = 0;
    int result1, result2, result3;
    int pValue1, pValue2, pValue3;

    @BeforeClass
    public static void setUp(){
        cartFrag = new CartFrag();
    }

    //testing method on cartFrag.java to get total final amount
    @Before
    public void getTotalBillAmount() {
        for(int x=0;x<5;x++) {
            pValue1 = tot_price1[x];
            sum1 += pValue1;
        }
        result1 = sum1;

        for(int y=0;y<5;y++) {
            pValue2 = tot_price2[y];
            sum2 += pValue2;
        }
        result2 = sum2;

        for(int z=0;z<5;z++) {
            pValue3 = tot_price3[z];
            sum3 += pValue3;
        }
        result3 = sum3;
    }

    @Test
    public void testTotalBillAmount(){
        assertEquals(150, result1,0);
        assertEquals(4011, result2,0);
        assertEquals(1834, result3,0);
    }

    @After
    public void clearAmount(){
        result1 = 0;
        result2 = 0;
        result3 = 0;
    }

    @AfterClass
    public static void  deleteTestGetTotalAmount(){
        cartFrag = null;
    }




}