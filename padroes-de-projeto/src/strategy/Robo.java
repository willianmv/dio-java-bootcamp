package strategy;

import strategy.strategies.IComportamento;

public class Robo {

    private IComportamento comportamento;

    public void setComportamento(IComportamento comportamento) {
        this.comportamento = comportamento;
    }

    public void mover(){
        comportamento.mover();;
    }

}
