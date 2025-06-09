import model.Board;
import model.Space;
import util.BoardTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static util.BoardTemplate.BOARD_TEMPLATE;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final int BOARD_LIMIT = 9;

    private static Board board;

    public static void main(String[] args) {

        final var positions = Stream.of(args)
                .collect(Collectors.toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));

        while(true){

            System.out.println("""
                ╔════════════════════════════════════════════════════╗
                ║                     MENU SUDOKU                    ║
                ╠════════════════════════════════════════════════════╣
                ║ Selecione uma das opções a seguir:                 ║
                ║                                                    ║
                ║ 1 - Iniciar novo jogo                              ║
                ║ 2 - Colocar um número                              ║
                ║ 3 - Remover um número                              ║
                ║ 4 - Visualizar jogo atual                          ║
                ║ 5 - Verificar status do jogo                       ║
                ║ 6 - Limpar jogo                                    ║
                ║ 7 - Finalizar jogo                                 ║
                ║ 0 - Sair                                           ║
                ╚════════════════════════════════════════════════════╝
                """);

            var option = sc.nextInt();

            switch (option){
                case 0 -> System.exit(0);
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> cleanGame();
                case 7 -> finishGame();
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void startGame(Map<String, String> positions) {
        if(verifyBoardStatus(board)){
            System.out.println("Board já iniciado.");

        } else{
            List<List<Space>> spaces = new ArrayList<>();
            for(int i = 0; i < BOARD_LIMIT; i++ ){
                spaces.add(new ArrayList<>());
                for(int j = 0; j < BOARD_LIMIT; j++){
                    var positionsConfig = positions.get("%s,%s".formatted(i, j));
                    var expected = Integer.parseInt(positionsConfig.split(",")[0]);
                    var fixed = Boolean.parseBoolean(positionsConfig.split(",")[1]);
                    var currentSpace = new Space(expected, fixed);
                    spaces.get(i).add(currentSpace);
                }
            }
            board = new Board(spaces);
            System.out.println("O jogo está pronto para ser iniciado.");
            showCurrentGame();
        }

    }

    private static void inputNumber() {
        if(verifyBoardStatus(board)){
            System.out.println("Informe a coluna: ");
            int col = runUntilGetValidNumber(0, 8);
            System.out.println("Informe a linha: ");
            int row = runUntilGetValidNumber(0, 8);
            System.out.printf("Informe o número para entrar na posição (%d, %d)\n", col, row);
            int value = runUntilGetValidNumber(1, 9);

            if(!board.changeValue(col, row, value)){
                System.out.printf("A posição %d, %d tem um valor fixo.\n", col, row);
            }

        } else{
            System.out.println("Inicie o board para jogar.");
        }

    }

    private static void removeNumber() {
        if(verifyBoardStatus(board)){
            System.out.println("Informe a coluna: ");
            int col = runUntilGetValidNumber(0, 8);
            System.out.println("Informe a linha: ");
            int row = runUntilGetValidNumber(0, 8);

            if(!board.clearValue(col, row)){
                System.out.printf("A posição %d, %d tem um valor fixo.\n", col, row);
            }

        }else{
            System.out.println("Inicie o board para jogar.");
        }
    }

    private static void showCurrentGame() {
        if(verifyBoardStatus(board)){
            var args = new Object[81];
            var argPos = 0;

            for(int i = 0; i < BOARD_LIMIT; i++){
                for(var col : board.getSpaces()){
                    args[argPos ++] = " " + ((isNull(col.get(i).getActualNumber())) ? " " : col.get(i).getActualNumber());
                }
            }
            System.out.println("Seu board se encontra da seguinte forma: ");
            System.out.printf(BOARD_TEMPLATE + "\n", args);

        } else{
            System.out.println("Inicie o board para jogar.");
        }
    }

    private static void showGameStatus() {
        if(verifyBoardStatus(board)){
            System.out.printf("O jogo se encontra com o status: %s\n",
                    board.getStatus().getLabel());

            if(board.hasErros()){
                System.out.println("o jogo contém erros.");
            } else{
                System.out.println("O jogo não contém erros.");
            }

        }else{
            System.out.println("Inicie o board para jogar.");
        }
    }

    private static void cleanGame() {
        if(verifyBoardStatus(board)){
            System.out.println("Tem certeza que deseja limpar tudo, perdendo o progresso nesse jogo? (S/N)");
            String confirm = runUntilGetYesOrNo();
            if(confirm.equalsIgnoreCase("s")) board.reset();

        }else{
            System.out.println("Inicie o board para jogar.");
        }
    }

    private static void finishGame() {
        if(verifyBoardStatus(board)){
            if(board.hasFinished()){
                System.out.println("Parabéns, você concluiu o jogo!");
                showCurrentGame();
                board = null;

            } else if(board.hasErros()){
                System.out.println("Seu jogo contém erros, verifique novamente.");
            } else{
                System.out.println("Seu jogo está incompleto.");
            }

        }else{
            System.out.println("Inicie o board para jogar.");
        }

    }

    private static boolean verifyBoardStatus(Board board){
        return nonNull(board);
    }

    private static int runUntilGetValidNumber(final int min, final int max){
        var current = sc.nextInt();
        while(current < min || current > max){
            System.out.printf("Insira um valor entre %d e %d\n", min, max);
            current = sc.nextInt();
        }
        return current;
    }

    private static String runUntilGetYesOrNo(){
        sc.nextLine();
        var text = sc.nextLine();
        while (!text.equalsIgnoreCase("s") && !text.equalsIgnoreCase("n")){
            System.out.println("Insira uma opção válida.");
            text = sc.nextLine();
        }
        return text;
    }
}
