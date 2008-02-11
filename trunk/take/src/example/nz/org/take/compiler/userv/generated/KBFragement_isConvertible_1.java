package example.nz.org.take.compiler.userv.generated;

import nz.org.take.rt.*;


/**
 * Class generated by the take compiler.
 * @version Mon Feb 11 13:49:16 NZDT 2008
 */
@SuppressWarnings("unchecked")
class KBFragement_isConvertible_1 {
    /**
     * Method generated for query isConvertible[in]
     * @param slot1 input parameter generated from slot 0
     * @return an iterator for instances of isConvertible
    */
    public static ResultSet<isConvertible> isConvertible_1(
        final example.nz.org.take.compiler.userv.domainmodel.Car slot1) {
        DerivationController _derivation = new DefaultDerivationController();
        ResultSet<isConvertible> _result = new ResultSet(KBFragement_isConvertible_1.isConvertible_1(
                    slot1, _derivation), _derivation);

        return _result;
    }

    /**
     * Method generated for query isConvertible[in]
     * @param source
     * @param target
     * @return an iterator
     * code generated using velocity template JPredicate_11.vm
    */
    static ResourceIterator<isConvertible> isConvertible_1(
        final example.nz.org.take.compiler.userv.domainmodel.Car slot1,
        final DerivationController _derivation) {
        _derivation.log("public boolean example.nz.org.take.compiler.userv.domainmodel.Car.isConvertible()",
            DerivationController.JAVA_METHOD);

        if (slot1.isConvertible()) {
            isConvertible result = new isConvertible();
            result.slot1 = slot1;

            return new SingletonIterator<isConvertible>(result);
        }

        return EmptyIterator.DEFAULT;
    }
}
