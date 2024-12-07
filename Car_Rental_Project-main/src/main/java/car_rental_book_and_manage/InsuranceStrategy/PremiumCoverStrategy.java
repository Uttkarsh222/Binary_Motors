package car_rental_book_and_manage.InsuranceStrategy;

public class PremiumCoverStrategy implements InsuranceStrategy {

  @Override
  public String getInsuranceTypeName() {
    return "Premium Cover";
  }

  @Override
  public String getDescription() {
    return "$0 EXCESS, $0 BOND\n\n"
            + "Windscreen & Tyre: Yes\n"
            + "Premium 24/7 Roadside Assistance: Yes\n"
            + "Lost Key Replacement: Yes";
  }

  @Override
  public double getDailyCost() {
    return 35.0;
  }

  @Override
  public double getDeposit() {
    return 0.0;
  }

  @Override
  public double getExcess() {
    return 0.0;
  }
}

