package example.nz.org.take.compiler.userv.generated;

import nz.org.take.rt.*;


/**
 * Class generated by the take compiler.
 * @version Fri Sep 07 12:07:28 NZST 2007
 */
@SuppressWarnings("unchecked")
class KBFragement_isCompact_1 {
    /**
     * Method generated for query isCompact[in]
     * @param slot1 input parameter generated from slot 0
     * @return an iterator for instances of isCompact
    */
    public static ResultSet<isCompact> isCompact_1(
        final example.nz.org.take.compiler.userv.domainmodel.Car slot1) {
        DerivationController _derivation = new DefaultDerivationController();
        ResultSet<isCompact> _result = new ResultSet(KBFragement_isCompact_1.isCompact_1(
                    slot1, _derivation), _derivation);

        return _result;
    }

    /**
     * Method generated for query isCompact[in]
     * @param source
     * @param target
     * @return an iterator
     * code generated using velocity template JPredicate_11.vm
    */
    static ResourceIterator<isCompact> isCompact_1(
        final example.nz.org.take.compiler.userv.domainmodel.Car slot1,
        final DerivationController _derivation) {
        _derivation.log("public boolean example.nz.org.take.compiler.userv.domainmodel.Car.isCompact()",
            DerivationController.JAVA_METHOD);

        if (slot1.isCompact()) {
            isCompact result = new isCompact();
            result.slot1 = slot1;

            return new SingletonIterator<isCompact>(result);
        }

        return EmptyIterator.DEFAULT;
    }
}
