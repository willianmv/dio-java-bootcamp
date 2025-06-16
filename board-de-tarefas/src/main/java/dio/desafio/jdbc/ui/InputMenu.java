package dio.desafio.jdbc.ui;

import java.util.Scanner;

public class InputMenu {

    private static final Scanner sc = new Scanner(System.in);

    public static long collectLong(String inputDescription){
        System.out.println(inputDescription);
        Long id = null;

        while (id == null) {
            String input = sc.nextLine().trim();
            try {
                id = Long.parseLong(input);
                if (id <= 0) {
                    System.out.println("ID DEVE SER UM NÚMERO POSITIVO: ");
                    id = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("ID INVÁLIDO.");
            }
        }
        return id;
    }

    public static String collectText(String inputDescription){
        System.out.println(inputDescription);
        String name = sc.nextLine().trim();

        while (name.isBlank() || name.length() < 3){
            System.out.println("NOME INVÁLIDO. DEVE TER NO MÍNIMO 3 CARATERES. TENTE NOVAMENTE: ");
            name = sc.nextLine().trim();
        }
        return name;
    }

}
