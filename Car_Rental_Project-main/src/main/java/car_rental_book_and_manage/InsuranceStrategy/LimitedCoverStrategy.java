package car_rental_book_and_manage.InsuranceStrategy;

public class LimitedCoverStrategy implements InsuranceStrategy {

  @Override
  public String getInsuranceTypeName() {
    return "Limited Cover";
  }

  @Override
  public String getDescription() {
    return "$2000 EXCESS, $2000 BOND\n\n"
            + "Windscreen & Tyre: No\n"
            + "Premium 24/7 Roadside Assistance: No\n"
            + "Lost Key Replacement: No";
  }

  @Override
  public double getDailyCost() {
    return 0.0;
  }

  @Override
  public double getDeposit() {
    return 2000.0;
  }

  @Override
  public double getExcess() {
    return 2000.0;
  }
}
