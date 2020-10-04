package com.example.myapplication1.IT19156170_Kajathees;


import com.example.myapplication1.CartFrag;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Unittest {
    static CartFrag cartfragment;
    int[] total_price1 = {1000,2000,300,2000,900};
    int sum1=0;
    int[] total_price2 = {1999,3999,2999,1999,1999};
    int sum2=0;
    int res1,res2;
    int cartVal1,cartVal2;

    @BeforeClass
    public static void setUp(){
        cartfragment = new CartFrag();
    }

    //test method for total amount

    @Before
    public void getTotalAmount(){
        int i,j;
        for(i=0;i<5;i++){
            cartVal1 = total_price1[i];
            sum1 = sum1+cartVal1;
        }
        res1 = sum1;

        for(j=0;j<5;j++){
            cartVal2 = total_price2[j];
            sum2 = sum2+cartVal2;
        }
        res2 = sum2;
    }

    @Test
    public void testTotalAmount(){
        assertEquals(6200,res1,0);
        assertEquals(12995,res2,0);
    }

    @After
    public void clearAmount(){
        res1=0;
        res2=0;
    }

    @AfterClass
    public static void deleteTotalAmount(){
        cartfragment = null;
    }

}
