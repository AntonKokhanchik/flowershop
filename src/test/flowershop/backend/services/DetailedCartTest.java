package flowershop.backend.services;

import flowershop.frontend.dto.OrderFlowerData;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DetailedCartTest {
    DetailedCart detailedCart1;

    @Before
    public void setUp() {
        Set<OrderFlowerData> flowersData = new HashSet<>();
        flowersData.add(new OrderFlowerData("f1", BigDecimal.valueOf(15), 5, null));
        flowersData.add(new OrderFlowerData("f2", BigDecimal.valueOf(50), 2, null));
        flowersData.add(new OrderFlowerData("f3", BigDecimal.valueOf(10), 3, null));

        detailedCart1 = new DetailedCart(flowersData);
    }

    @Test
    public void getSum() {
        BigDecimal expected =  BigDecimal.valueOf(5*15 + 2*50 + 3*10);
        assertEquals(expected, detailedCart1.getSum());
    }

    @Test
    public void getResult() {
        BigDecimal expected = BigDecimal.valueOf((5*15 + 2*50 + 3*10) * 0.8).setScale(2, RoundingMode.DOWN);
        assertEquals(expected, detailedCart1.getResult(20));
    }
}