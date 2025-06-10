import singleton.eager.SingletonEager;
import singleton.lazy.SingletonLazy;
import strategy.Robo;
import strategy.strategies.ComportamentoDefensivo;
import strategy.strategies.ComportamentoNormal;
import strategy.strategies.ComportamentoOfensivo;
import strategy.strategies.IComportamento;

public class PatternsTests {

    public static void main(String[] args) {

        System.out.println("TESTANDO PADRÃO SINGLETON");

        SingletonLazy lazy = SingletonLazy.getInstance();
        System.out.println(lazy);
        lazy = SingletonLazy.getInstance();
        System.out.println(lazy);

        SingletonEager eager = SingletonEager.getInstance();
        System.out.println(eager);
        eager = SingletonEager.getInstance();
        System.out.println(eager);

        System.out.println("===========================");

        System.out.println("TESTANDO PADRÃO STRATEGY");

        IComportamento comportamentoNormal = new ComportamentoNormal();
        IComportamento comportamentoDefensivo = new ComportamentoDefensivo();
        IComportamento comportamentoOfensivo = new ComportamentoOfensivo();

        Robo robo = new Robo();

        robo.setComportamento(comportamentoNormal);
        robo.mover();

        robo.setComportamento(comportamentoDefensivo);
        robo.mover();

        robo.setComportamento(comportamentoOfensivo);
        robo.mover();;

        System.out.println("===========================");

    }

}
