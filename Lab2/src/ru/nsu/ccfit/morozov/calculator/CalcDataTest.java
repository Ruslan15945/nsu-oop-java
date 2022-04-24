package ru.nsu.ccfit.morozov.calculator;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CalcDataTest {

    @Test
    public void PushPop() {
        CalcData data = new CalcData();
        data.push(10.96);
        data.push(0.0001);
        Assert.assertEquals(new ArrayList<>(List.of(10.96, 0.0001)), data.getStack());

        data.pop();
        Assert.assertEquals(new ArrayList<>(List.of(10.96)), data.getStack());

        data.pop();
        Assert.assertEquals(new ArrayList<>(), data.getStack());
    }

    @Test
    public void peek() {
        CalcData data = new CalcData();
        data.push(10.);
        data.push(-3.14);
        Assert.assertEquals(-3.14, data.peek(), 0.000001);
    }

    @Test
    public void AddGet() {
        CalcData data = new CalcData();
        Assert.assertNull(data.get("Test"));

        data.add("Test", 13.57);
        Assert.assertNotNull(data.get("Test"));
        Assert.assertEquals(13.57, data.get("Test"), 0.00001);
    }

    @Test
    public void SizeEmpty() {
        CalcData data = new CalcData();
        Assert.assertTrue(data.empty());

        data.push(1.1);
        Assert.assertFalse(data.empty());
        Assert.assertEquals(1, data.size());

        data.push(2.2);
        data.pop();
        data.pop();
        Assert.assertTrue(data.empty());

    }

}