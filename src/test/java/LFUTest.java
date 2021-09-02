import Cache.Lfu;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LFUTest {
    @Test
    public void lfuReadTest() {
        Lfu lfu = new Lfu(4);
        lfu.length = 3;
        lfu.array[0] = 1;
        assertEquals(1,lfu.read(1));
        assertEquals(0, lfu.read(2));
    }

    @Test
    public void lfuWriteTest() {
        int[] demo = {2,9,9,9,2,2,1,6,5,1,5,7,1,7,1};
        Lfu lfu = new Lfu(4);
        for (int i = 0; i < demo.length; i++) {
            lfu.write(demo[i]);
            assertEquals(1, lfu.write(demo[i]));
        }
    }

    @Test
    public void lfuRatioTest() {
        int[] demo = {2,9,9,9,2,5,1,6,5,1,5,7,1,7,1};
        Lfu lfu = new Lfu(4);
        for (int i = 0; i < demo.length; i++)                           //LFU
            lfu.write(demo[i]);

//        assertTrue("Hit rate is wrong", lfu.hitRate() == 0.2667);
        assertEquals("Hit rate not equals", 0.2667, lfu.hitRate(), 0.01);
    }
}
