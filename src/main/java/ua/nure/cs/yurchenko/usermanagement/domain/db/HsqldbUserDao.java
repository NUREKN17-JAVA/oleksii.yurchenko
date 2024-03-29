package ua.nure.cs.yurchenko.usermanagement.domain.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import ua.nure.cs.yurchenko.usermanagement.domain.User;

class HsqldbUserDao implements UserDao {

	private static final String DELETE_QUERY = "DELETE FROM USERS WHERE id = ?";
	private static final String SELECT_ALL_QUERY = "SELECT id, firstname, lastname, dateofbirth FROM users";
	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?, ?, ?)";
	private static final String FIND_QUERY = "SELECT * FROM users WHERE id = ?";
	private static final String UPDATE_QUERY = "UPDATE USERS SET firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?";
	private static final String SELECT_BY_NAMES = "SELECT id, firstname, lastname, dateofbirth FROM users WHERE firstname = ? AND lastname = ?";
	private ConnectionFactory connectionFactory;
	
	public HsqldbUserDao(ConnectionFactory connectionFactory2) {
		this.connectionFactory=connectionFactory;
	}
	
	
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}


	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}


	@Override
	public User create(User user) throws DatabaseException {
		// TODO Auto-generated method stub
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
			int n = statement.executeUpdate();
			if (n != 1) {
				throw new DatabaseException("Number of the inserted rows: " + n);
			}
			CallableStatement callabelStatement = connection.prepareCall("call IDENTITY()");
			ResultSet keys = callabelStatement.executeQuery();
			if (keys.next()) {
				user.setId(new Long(keys.getLong(1)));
			}
			keys.close();
			callabelStatement.close();
			statement.close();
			connection.close();
			return user;
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException(e);
		}
	}

	@Override
	public void update(User user) throws DatabaseException {
		// TODO Auto-generated method stub
		try {
            Connection connection = connectionFactory.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            preparedStatement.setLong(4, user.getId());

            int insertedRows = preparedStatement.executeUpdate();

            if (insertedRows != 1) {
                throw new DatabaseException("Number of inserted rows: " + insertedRows);
            }

            connection.close();
            preparedStatement.close();
        } catch (DatabaseException e) {
            throw new DatabaseException(e.toString());
        }catch (SQLException e) {
            throw new DatabaseException(e.toString());
        }

	}

	@Override
	public void delete(User user) throws DatabaseException {
		// TODO Auto-generated method stub
		try {
            Connection connection = connectionFactory.createConnection();

            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setLong(1, user.getId());

            int removedRows = statement.executeUpdate();

            if (removedRows != 1) {
                throw new DatabaseException("Number of removed rows: " + removedRows);
            }

            connection.close();
            statement.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
	}

	@Override
	public User find(Long id) throws DatabaseException {
		// TODO Auto-generated method stub
		Connection connection = connectionFactory.createConnection();
		User user = new User();
		try {
			PreparedStatement statement = connection.prepareStatement(FIND_QUERY);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				user = new User();
				user.setId(resultSet.getLong(1));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		return user;
	}

	@Override
	public Collection findAll() throws DatabaseException {
		// TODO Auto-generated method stub
		Collection result = new LinkedList();
		
		try {
			Connection connection = connectionFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);
			while (resultSet.next()) {
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
				result.add(user);
			}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException(e);
		}
		
		return result;
	}


	@Override
	public Collection find(String firstName, String lastName) throws DatabaseException {
		// TODO Auto-generated method stub
		Collection result = new LinkedList();
		
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.createStatement(SELECT_BY_NAMES);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
				result.add(user);
			}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			throw e;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DatabaseException(e);
		}
		
		return result;
	}

}
