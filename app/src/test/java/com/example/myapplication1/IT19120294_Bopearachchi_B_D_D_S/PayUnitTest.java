package com.example.myapplication1.IT19120294_Bopearachchi_B_D_D_S;

import com.example.myapplication1.PurchaseConfirmation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PayUnitTest {
    static PurchaseConfirmation confirmation;
    private double v,val1,val2,val3,val4,val5,val6,output1,output2,output3;

    @BeforeClass
    public static void CreatePaymentObject(){
        confirmation = new PurchaseConfirmation();}

    @Before
    public void getTotValue(){
        v = 60.0/100.0;

        val1= 300;
        val2= val1*v;
        output1=val2;

        val3= 5000;
        val4=val3*v;
        output2=val4;

        val5=522;
        val6=val5*v;
        output3=val6;

    }

    @Test
    public void testTotValue(){
        assertEquals(180.0,output1,0);
        assertEquals(3000.0,output2,0);
        assertEquals(313.2,output3,0);
    }

    @After
    public void clearAmount(){
        output1=0;
        output2=0;
        output3=0;
    }

    @AfterClass
    public static void deleteTotValue(){
        confirmation=null;
    }
}
