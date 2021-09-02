package Cache;

public class Lru implements Cache{

    public int[] array;        //Cache块数组

    int[] counter;      //计数器数组

    int size;           //cache块数

    public int length = 0;     //当前块数

    double total = 0;          //读地址的总次数

    double hit = 0;            //命中次数

    public Lru(int size) {      //构造cache
        this.size = size;
        array = new int[size];
        counter = new int[size];
    }

    @Override
    public int read(int address) {
        for (int i = 0; i < length; i++)
            if(array[i] == address)
                return address;

        return 0;
    }

    @Override
    public int write(int address) {
        total = total+1;
        if (read(address) != 0)
            hit = hit+1;

        if (length < size && read(address) == 0) {                    //当cache没满时，每写入一个地址，之前的所有地址计数器自增1
            array[length] = address;
            for (int i = 0; i < length; i++)
                counter[i]++;
            length++;
        }
        else {
            if (read(address) != 0) {
                for (int i = 0; i < length; i++) {  //当cache已满时，当写入地址存在于cache中，则目标模块计数器清0，其他模块计数器自增1
                    if(array[i] != address)
                        counter[i]++;
                    else
                        counter[i] = 0;
                }
            }
            else {                              //执行LRU置换算法，即当cache已满时且目标模块不存在于cache中，此时需淘汰模块
                int max = counter[0], location = 0;
                for (int i = 1; i < length; i++) {
                    if (counter[i] > max) {
                        max = counter[i];
                        location = i;
                    }
                }
                array[location] = address;
                for (int i = 0; i < length; i++) {
                    if(array[i] != address)
                        counter[i]++;
                    else
                        counter[i] = 0;
                }
            }
        }
        return 1;
    }

    @Override
    public double hitRate() {
        double ratio = hit / total;
        return ratio;
    }
}
