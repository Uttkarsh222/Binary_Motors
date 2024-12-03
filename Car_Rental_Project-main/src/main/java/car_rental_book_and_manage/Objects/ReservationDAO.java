package car_rental_book_and_manage.Objects;

import java.time.LocalDate;
import java.util.List;

import car_rental_book_and_manage.Payment.CardPayment;

/** Interface for reservation data access operations. */
public interface ReservationDAO {


  void saveReservationAndPayment(Reservation reservation, CardPayment payment);

  /** Retrieves all reservations from the database and adds them to the model. */
  void retrieveAllReservations();


  Reservation getReservationForClient(int clientId);


  List<Reservation> getReservationsToDrop(LocalDate date);


  void deleteReservationAndPayment(int reservationId);

  /** Retrieves the latest saved reservation from the database and adds it to the model. */
  void retrieveLatestReservationToSave();
}
