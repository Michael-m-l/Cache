package Cache;

public class Lfu implements Cache{
    public int[] array;        //Cache块数组

    int[] counterLfu;      //计数器数组1

    int[] counterLru;      //计数器数组2

    int size;           //cache块数

    public int length = 0;     //当前块数

    double total = 0;          //读地址的总次数

    double hit = 0;            //命中次数

    public Lfu(int size) {      //构造LFU
        this.size = size;
        array = new int[size];
        counterLfu = new int[size];
        counterLru = new int[size];
    }

    @Override
    public int read(int address) {
        for (int i = 0; i < length; i++)
            if (array[i] == address)
                return address;

        return 0;
    }

    @Override
    public int write(int address) {
        total = total+1;
        if (read(address) != 0)
            hit = hit+1;

        if (length < size && read(address) == 0) {
            array[length] = address;
            counterLfu[length++] = 1;
            for (int i = 0; i < length; i++)
                counterLru[i]++;
        }
        else {
            if (read(address) != 0) {
                for (int i = 0; i < length; i++)   //当cache已满时，当写入地址存在于cache中，则目标模块计数器+1
                    if(array[i] == address) {
                        counterLfu[i]++;
                        break;
                    }
                for (int i = 0; i < length; i++) {  //当cache已满时，当写入地址存在于cache中，则目标模块计数器清0，其他模块计数器自增1
                    if(array[i] != address)
                        counterLru[i]++;
                    else
                        counterLru[i] = 0;
                }
            }
            else {                              //执行LFU替换算法，选择计数器最小的块替换
                int min = counterLfu[0], max = counterLru[0],location = 0;
                for (int i = 1; i < length; i++) {          //查找LFU计数器最小的块且LRU计数器最大的块
                    if (counterLfu[i] < min) {
                        min = counterLfu[i];
                        max = counterLru[i];
                        location = i;
                    }
                    else if (counterLfu[i] == min && counterLru[i] > max) {
                        min = counterLfu[i];
                        max = counterLru[i];
                        location = i;
                    }
                }
                array[location] = address;
                for (int i = 0; i < length; i++) {
                    if(array[i] != address)
                        counterLru[i]++;
                    else
                        counterLru[i] = 0;
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
