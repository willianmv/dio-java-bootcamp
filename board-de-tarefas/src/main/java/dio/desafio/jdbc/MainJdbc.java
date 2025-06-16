package dio.desafio.jdbc;

import dio.desafio.jdbc.dao.impl.BoardDAO;
import dio.desafio.jdbc.ui.BoardMenu;
import dio.desafio.jdbc.util.MigrationExecutor;

public class MainJdbc {

    public static void main(String[] args) {
        MigrationExecutor.loadMigrations();

        BoardDAO boardDAO = new BoardDAO();
        BoardMenu boardMenu = new BoardMenu();
        boardMenu.showMenu();


    }

}
