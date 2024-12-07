package car_rental_book_and_manage.InsuranceStrategy;


public class InsuranceManager {
  private InsuranceStrategy strategy;

  public void setStrategy(InsuranceStrategy strategy) {
    this.strategy = strategy;
  }

  public InsuranceStrategy getStrategy() {
    return strategy;
  }

  public String getDescription() {
    if (strategy == null) {
      return "No strategy selected";
    } else {
      return strategy.getDescription();
    }
  }

  public double getDailyCost() {
    return strategy.getDailyCost();
  }

  public double getDeposit() {
    return strategy.getDeposit();
  }

  public double getExcess() {
    return strategy.getExcess();
  }

  public InsuranceStrategy getStrategyByType(String type) {
    switch (type) {
      case "Basic Cover":
        return new BasicCoverStrategy();
      case "Limited Cover":
        return new LimitedCoverStrategy();
      case "Premium Cover":
        return new PremiumCoverStrategy();
      default:
        throw new IllegalArgumentException("Unknown insurance type: " + type);
    }
  }
}
