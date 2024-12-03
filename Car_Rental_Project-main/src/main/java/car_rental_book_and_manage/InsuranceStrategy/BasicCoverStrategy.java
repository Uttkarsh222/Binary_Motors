package car_rental_book_and_manage.InsuranceStrategy;

public class BasicCoverStrategy implements InsuranceStrategy {

  @Override
  public String getInsuranceTypeName() {
    return "Basic Cover";
  }

  @Override
  public String getDescription() {
    return "$500 EXCESS, $500 BOND\n\n"
               + "Windscreen & Tyre: No\n"
               + "Premium 24/7 Roadside Assistance: No\n"
               + "Lost Key Replacement: No";
  }


  @Override
  public double getDailyCost() {
    return 24.0;
  }


  @Override
  public double getDeposit() {
    return 500.0;
  }


  @Override
  public double getExcess() {
    return 500.0;
  }
}
