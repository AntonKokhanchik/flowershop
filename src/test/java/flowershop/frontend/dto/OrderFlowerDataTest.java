package flowershop.frontend.dto;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OrderFlowerDataTest {
    @Test
    public void getFullPrice() {
        OrderFlowerData flower1 = new OrderFlowerData("flower1", BigDecimal.valueOf(15), 5, 1L);
        OrderFlowerData flower2 = new OrderFlowerData("flower2", BigDecimal.valueOf(50), 2, 2L);
        OrderFlowerData flower3 = new OrderFlowerData("flower3", BigDecimal.valueOf(10), 3, 3L);

        assertEquals(flower1.getFullPrice(), BigDecimal.valueOf(5*15));
        assertEquals(flower2.getFullPrice(), BigDecimal.valueOf(2*50));
        assertEquals(flower3.getFullPrice(), BigDecimal.valueOf(3*10));
    }
}