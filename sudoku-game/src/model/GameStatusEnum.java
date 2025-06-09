package model;

public enum GameStatusEnum {

    NON_STARTED("Não iniciado"),
    COMPLETE("Completo"),
    INCOMPLETE("Incompleto");

    private final String label;

    GameStatusEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
