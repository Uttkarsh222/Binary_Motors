package car_rental_book_and_manage.Objects;

public interface ClientDAO {

    void saveClient(Client client);

    void updateClient(Client client);

    void retrieveAllClients();

    int getNumOfClients();

    boolean doesUserNameExist(String username);

    boolean isLoginCredentialsValid(String username, String password);

    Client getClient(String username);

    void retrieveLatestClientToSave();

    void retrieveClientByIdToUpdate(int clientId);
}
