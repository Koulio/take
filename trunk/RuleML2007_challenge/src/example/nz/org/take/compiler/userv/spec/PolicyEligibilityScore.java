package example.nz.org.take.compiler.userv.spec;

/**
 * Class generated by the take compiler.
 * This class represents the predicate policyEligibilityScore
 * @version Thu Oct 18 23:01:31 NZDT 2007
 */
public class PolicyEligibilityScore {
    public example.nz.org.take.compiler.userv.domainmodel.Car car;
    public example.nz.org.take.compiler.userv.domainmodel.Driver driver;
    public int score;

    public PolicyEligibilityScore(
        example.nz.org.take.compiler.userv.domainmodel.Car car,
        example.nz.org.take.compiler.userv.domainmodel.Driver driver, int score) {
        super();
        this.car = car;
        this.driver = driver;
        this.score = score;
    }

    public PolicyEligibilityScore() {
        super();
    }
}
