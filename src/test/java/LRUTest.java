import Cache.Lru;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LRUTest {
    @Test
    public void lruReadTest() {
        Lru lru = new Lru(4);
        lru.length = 3;
        lru.array[0] = 1;
        assertEquals(1,lru.read(1));
        assertEquals(0, lru.read(2));
    }

    @Test
    public void lruWriteTest() {
        int[] demo = {8,2,3,4,4,3,8,8,2,4,3,4,6,3,9};
        Lru lru = new Lru(4);
        for (int i = 0; i < demo.length; i++) {
            lru.write(demo[i]);
            assertEquals(1, lru.write(demo[i]));
        }
    }

    @Test
    public void lruRatioTest() {
        //        RandomSet randomSet = new RandomSet(15);
//        int[] demo = {1,8,3,7,6,2,5,3,9,1,8,2,5,3,1};     //命中率均为0
//        int[] demo = {8,2,3,4,4,3,8,8,2,4,3,4,6,3,9};     //命中率均为60%
//        int[] demo = {2,9,9,9,2,5,1,6,5,1,5,7,1,7,1};     //LRU 60%  LFU 26.67%
//        int[] demo = {1,1,2,2,3,3,4,5,6,1,2,3,6,1,2};     //LRU 40%  LFU 60%
        int[] demo = {2,9,9,9,2,5,1,6,5,1,5,7,1,7,1};
        Lru lru = new Lru(4);
        for (int i = 0; i < demo.length; i++)                        // LRU
            lru.write(demo[i]);

        assertTrue("Hit rate is wrong", lru.hitRate() == 0.6);
        assertEquals("Hit rate not equals", 0.6, lru.hitRate(), 0.001);
    }
}
