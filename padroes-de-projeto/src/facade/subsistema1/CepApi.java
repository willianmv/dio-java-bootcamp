package facade.subsistema1;

public class CepApi {

    private static final CepApi instancia = new CepApi();

    private CepApi() {}

    public static CepApi getInstance(){
        return instancia;
    }

    public String recuperarEstado(String cep){
        return "SP";
    }

    public String recuperarCidade(String cep){
        return "São Paulo";
    }

}
