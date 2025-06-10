package strategy.strategies;

public class ComportamentoDefensivo implements IComportamento{

    @Override
    public void mover() {
        System.out.println("Movendo defensivamente...");
    }
}
