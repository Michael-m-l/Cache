package Cache;

public interface Cache {

    int read(int address);

    int write(int address);

    double hitRate();
}
