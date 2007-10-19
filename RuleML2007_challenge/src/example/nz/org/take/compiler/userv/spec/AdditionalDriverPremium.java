package example.nz.org.take.compiler.userv.spec;

/**
 * Class generated by the take compiler.
 * This class represents the predicate additionalDriverPremium
 * @version Thu Oct 18 23:01:31 NZDT 2007
 */
public class AdditionalDriverPremium {
    public example.nz.org.take.compiler.userv.domainmodel.Driver driver;
    public int premium;

    public AdditionalDriverPremium(
        example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        int premium) {
        super();
        this.driver = driver;
        this.premium = premium;
    }

    public AdditionalDriverPremium() {
        super();
    }
}
