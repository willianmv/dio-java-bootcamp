package facade.subsistema2;

public class CrmService {

    private CrmService(){}

    public static void graverCliente(String nome, String cep, String cidade, String estado){
        System.out.println("Cliente salvo no sistema de CRM com os seguintes dados: ");
        System.out.println();
        System.out.println(nome);
        System.out.println(cep);
        System.out.println(cidade);
        System.out.println(estado);
    }

}
