package facade;

import facade.subsistema1.CepApi;
import facade.subsistema2.CrmService;

public class Facade {

    public void migrarCliente(String nome, String cep){
        String cidade = CepApi.getInstance().recuperarCidade(cep);
        String estado = CepApi.getInstance().recuperarEstado(cep);

        CrmService.graverCliente(nome, cep, cidade, estado);
    }

}
