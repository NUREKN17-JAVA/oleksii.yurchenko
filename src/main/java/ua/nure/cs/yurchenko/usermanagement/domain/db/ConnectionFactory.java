package ua.nure.cs.yurchenko.usermanagement.domain.db;

import java.sql.Connection;

public interface ConnectionFactory {
	Connection createConnection() throws DatabaseException;
}
