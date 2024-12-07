package car_rental_book_and_manage.Payment;

import java.time.LocalDate;

/** Class representing a credit card payment. */
public class CreditCardPayment extends CardPayment {

  public CreditCardPayment(
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
        "Credit Card",
        amount,
        rentalId,
        clientId,
        paymentDate);
  }
}
