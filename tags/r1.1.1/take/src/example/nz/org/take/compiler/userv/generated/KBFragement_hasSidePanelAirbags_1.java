package example.nz.org.take.compiler.userv.generated;

import nz.org.take.rt.*;


/**
 * Class generated by the take compiler.
 * @version Mon Aug 20 21:59:11 NZST 2007
 */
@SuppressWarnings("unchecked")
class KBFragement_hasSidePanelAirbags_1 {
    /**
     * Method generated for query hasSidePanelAirbags[in]
     * @param slot1 input parameter generated from slot 0
     * @return an iterator for instances of _hasSidePanelAirbags
    */
    public static ResultSet<_hasSidePanelAirbags> hasSidePanelAirbags_1(
        final example.nz.org.take.compiler.userv.domainmodel.Car slot1) {
        DerivationController _derivation = new DefaultDerivationController();
        ResultSet<_hasSidePanelAirbags> _result = new ResultSet(KBFragement_hasSidePanelAirbags_1.hasSidePanelAirbags_1(
                    slot1, _derivation), _derivation);

        return _result;
    }

    /**
     * Method generated for query hasSidePanelAirbags[in]
     * @param source
     * @param target
     * @return an iterator
     * code generated using velocity template nz/org/take/compiler/reference/JPredicate_11.vm
    */
    static ResourceIterator<_hasSidePanelAirbags> hasSidePanelAirbags_1(
        final example.nz.org.take.compiler.userv.domainmodel.Car slot1,
        final DerivationController _derivation) {
        _derivation.log("public boolean example.nz.org.take.compiler.userv.domainmodel.Car.hasSidePanelAirbags()",
            DerivationController.JAVA_METHOD);

        if (slot1.hasSidePanelAirbags()) {
            _hasSidePanelAirbags result = new _hasSidePanelAirbags();
            result.slot1 = slot1;

            return new SingletonIterator<_hasSidePanelAirbags>(result);
        }

        return EmptyIterator.DEFAULT;
    }
}