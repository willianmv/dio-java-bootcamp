package singleton.eager;

public class SingletonEager {

    private static final SingletonEager instancia = new SingletonEager();

    private SingletonEager() {}

    public static SingletonEager getInstance(){
        return instancia;
    }

}
