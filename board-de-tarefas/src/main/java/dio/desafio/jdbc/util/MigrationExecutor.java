package dio.desafio.jdbc.util;

import org.flywaydb.core.Flyway;

public final class MigrationExecutor {

    public static void loadMigrations(){

        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        // Configura o Flyway manualmente
        Flyway flyway = Flyway.configure()
                .dataSource(url, user, password)
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();

        System.out.println("Migrações aplicadas com sucesso!");

    }

}
