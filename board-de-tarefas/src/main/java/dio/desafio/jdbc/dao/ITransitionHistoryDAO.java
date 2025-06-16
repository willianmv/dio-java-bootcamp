package dio.desafio.jdbc.dao;

public interface ITransitionHistoryDAO<T, U> {

    T move(U entityId, U fromId, U toId);

}
