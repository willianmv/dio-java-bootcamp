package strategy.strategies;

public class ComportamentoOfensivo implements IComportamento{

    @Override
    public void mover() {
        System.out.println("Movendo ofensivamente...");
    }
}
