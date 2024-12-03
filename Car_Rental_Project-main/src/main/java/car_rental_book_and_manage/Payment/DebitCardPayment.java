package car_rental_book_and_manage.Payment;

import java.time.LocalDate;

/** Class representing a debit card payment. */
public class DebitCardPayment extends CardPayment {

  public DebitCardPayment(
      String cardNumber,
      String cardHolderName,
      String cvv,
      String expiryDate,
      double amount,
      int rentalId,
      int clientId,
      LocalDate paymentDate) {
    super(
        cardNumber,
        cardHolderName,
        cvv,
        expiryDate,
        "Debit Card",
        amount,
        rentalId,
        clientId,
        paymentDate);
  }
}
