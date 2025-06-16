package dio.desafio.jdbc.dao;

public interface IBlockHistoryDAO<T, U> {

    T registerBlock(U cardId, String reason);

    T registerUnblock(U cardId, String reason);

}
