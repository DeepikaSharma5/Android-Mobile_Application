package com.example.myapplication1.IT19148632_DEEPIKA_S;

import com.example.myapplication1.PurchaseConfirmation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PaymentTest {

    static PurchaseConfirmation purchaseConfirmation;
    private double no1,no2,no3,no4,m,no5,no6,no7,no8,result,result1,result2,result3;

    @BeforeClass
    public static void CreatePaymentObject(){
        purchaseConfirmation = new PurchaseConfirmation();
    }

    @Before
    public void getTotalAmount(){
        m = 80.0/100.0;

        no1 = 100;
        no2 = no1*m;
        result = no2;

        no3 = 1000;
        no4 = no3 * m;
        result1 = no4;

        no5 = 356;
        no6 = no5 * m;
        result2 = no6;

        no7 = 37465;
        no8 = no7 * m;
        result3 = no8;
    }

    @Test
    public void testTotalAmount(){
        assertEquals(80.0,result,0);
        assertEquals(800.0,result1,0);
        assertEquals(284.8,result2,0);
        assertEquals(29972.0,result3,0);
    }

    @After
    public void clearAmount(){
        result = 0;
        result1 = 0;
        result2 = 0;
        result3 = 0;
    }

    @AfterClass
    public static void deletePaymentObject(){
        purchaseConfirmation = null;
    }

}
