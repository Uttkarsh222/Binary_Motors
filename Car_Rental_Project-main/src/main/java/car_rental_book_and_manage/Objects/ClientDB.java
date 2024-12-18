package car_rental_book_and_manage.Objects;

import car_rental_book_and_manage.App;
import car_rental_book_and_manage.Controllers.ClientHashing;
import car_rental_book_and_manage.Utility.DataManager;
import car_rental_book_and_manage.Utility.PIIHashManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class ClientDB implements ClientDAO {

  private static final DataModel model = DataModel.getInstance();

  @Override
  public synchronized void saveClient(Client client) {
    App.clientdbExecutor.execute(
        () -> {
          String query =
              "INSERT INTO CLIENT (Fname, Username, Pword, Phone_no, License_no) VALUES (?, ?, ?, ?, ?)";
          try (Connection connection = DataManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement =
                connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
              setClientStatementParams(statement, client);
              statement.executeUpdate();
              connection.commit();
              retrieveLatestClientToSave();
              model.setNumOfClients(String.valueOf(getNumOfClients()));
              System.out.println("Saved new client data");
            } catch (SQLException e) {
              connection.rollback();
              handleSQLException(e);
            }
          } catch (SQLException e) {
            handleSQLException(e);
          }
        });
  }

  @Override
  public synchronized void updateClient(Client client) {
    App.clientdbExecutor.execute(
        () -> {
          String query = "UPDATE CLIENT SET Fname = ?, Phone_no = ?, License_no = ? WHERE C_Id = ?";
          try (Connection connection = DataManager.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
              statement.setString(1, client.getFirstName());
              statement.setString(2, client.getPhoneNo());
              statement.setString(3, client.getLicenseNo());
              statement.setInt(4, client.getClientId());
              statement.executeUpdate();
              connection.commit();
              retrieveClientByIdToUpdate(client.getClientId());
              System.out.println("Updated client data");
            } catch (SQLException e) {
              connection.rollback();
              handleSQLException(e);
            }
          } catch (SQLException e) {
            handleSQLException(e);
          }
        });
  }

  @Override
  public synchronized void retrieveAllClients() {
    App.clientdbExecutor.execute(
        () -> {
          String sql = "SELECT * FROM CLIENT";
          try (Connection connection = DataManager.getConnection();
              PreparedStatement statement = connection.prepareStatement(sql);
              ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
              Client client = mapResultSetToClient(resultSet);
              model.addClient(client);
            }
          } catch (SQLException e) {
            handleSQLException(e);
          }
          System.err.println("Retrieved all clients");
        });
  }

  @Override
  public synchronized void retrieveLatestClientToSave() {
    App.clientdbExecutor.execute(
        () -> {
          String sql = "SELECT * FROM CLIENT ORDER BY C_Id DESC LIMIT 1";
          try (Connection connection = DataManager.getConnection();
              PreparedStatement statement = connection.prepareStatement(sql);
              ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
              Client client = mapResultSetToClient(resultSet);
              model.addClient(client);
            }
          } catch (SQLException e) {
            handleSQLException(e);
          }
          System.err.println("Retrieved latest client");
        });
  }

  @Override
  public synchronized void retrieveClientByIdToUpdate(int clientId) {
    App.clientdbExecutor.execute(
        () -> {
          String sql = "SELECT * FROM CLIENT WHERE C_Id = ?";
          try (Connection connection = DataManager.getConnection();
              PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientId);
            try (ResultSet resultSet = statement.executeQuery()) {
              if (resultSet.next()) {
                Client client = mapResultSetToClient(resultSet);
                model.updateClient(client);
              }
            }
          } catch (SQLException e) {
            handleSQLException(e);
          }
          System.err.println("Retrieved client by ID: " + clientId);
        });
  }

  @Override
  public synchronized int getNumOfClients() {
    int totalClients = 0;
    String sql = "SELECT COUNT(*) AS totalClients FROM CLIENT";
    try (Connection connection = DataManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {
      if (resultSet.next()) {
        totalClients = resultSet.getInt("totalClients");
      }
    } catch (SQLException e) {
      handleSQLException(e);
    }
    System.out.println("Got number of clients");
    return totalClients;
  }

  @Override
  public synchronized boolean doesUserNameExist(String username) {
    String sql = "SELECT COUNT(*) AS count FROM CLIENT WHERE USERNAME = ?";
    try (Connection connection = DataManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getInt("count") > 0;
        }
      }
    } catch (SQLException e) {
      handleSQLException(e);
    }
    return false;
  }

  @Override
  public synchronized boolean isLoginCredentialsValid(String username, String password) {

    Map<String, Client> clientData = ClientHashing.clientData;

    Client currentUser = clientData.get(username);

    if(currentUser != null) return PIIHashManager.checkPassword(password, currentUser.getPassword());

    else{
      String sql = "SELECT Pword FROM CLIENT WHERE Username = ?";
      try (Connection connection = DataManager.getConnection();
           PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, username);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            String storedHashedPassword = resultSet.getString("Pword");
            // Verify the provided password with the stored hashed password
            return PIIHashManager.checkPassword(password, storedHashedPassword);
          }
        }
      } catch (SQLException e) {
        handleSQLException(e);
      }
      return false;
    }

  }

  @Override
  public synchronized Client getClient(String username) {
    String sql = "SELECT * FROM CLIENT WHERE Username = ?";
    try (Connection connection = DataManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return mapResultSetToClient(resultSet);
        }
      }
    } catch (SQLException e) {
      handleSQLException(e);
    }
    return null;
  }

  private void setClientStatementParams(PreparedStatement statement, Client client)
      throws SQLException {
    statement.setString(1, client.getFirstName());
    statement.setString(2, client.getUsername());
    statement.setString(3, client.getPassword());
    statement.setString(4, client.getPhoneNo());
    statement.setString(5, client.getLicenseNo());
  }

  private Client mapResultSetToClient(ResultSet resultSet) throws SQLException {
    Client client = new Client();
    client.setClientId(resultSet.getInt("C_Id"));
    client.setFirstName(resultSet.getString("Fname"));
    client.setUsername(resultSet.getString("Username"));
    client.setPassword(resultSet.getString("Pword"));
    client.setPhoneNo(resultSet.getString("Phone_no"));
    client.setLicenseNo(resultSet.getString("License_no"));
    return client;
  }

  private void handleSQLException(SQLException e) {
    System.err.println("Database error: " + e.getMessage());
  }
}
