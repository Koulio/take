package example.nz.org.take.compiler.userv.generated;

import nz.org.take.rt.*;


/**
 * Class generated by the take compiler.
 * @version Fri Sep 07 12:07:28 NZST 2007
 */
@SuppressWarnings("unchecked")
class KBFragement_hasDriversTrainingFromSchool_1 {
    /**
     * Method generated for query hasDriversTrainingFromSchool[in]
     * @param slot1 input parameter generated from slot 0
     * @return an iterator for instances of hasDriversTrainingFromSchool
    */
    public static ResultSet<hasDriversTrainingFromSchool> hasDriversTrainingFromSchool_1(
        final example.nz.org.take.compiler.userv.domainmodel.Driver slot1) {
        DerivationController _derivation = new DefaultDerivationController();
        ResultSet<hasDriversTrainingFromSchool> _result = new ResultSet(KBFragement_hasDriversTrainingFromSchool_1.hasDriversTrainingFromSchool_1(
                    slot1, _derivation), _derivation);

        return _result;
    }

    /**
     * Method generated for query hasDriversTrainingFromSchool[in]
     * @param source
     * @param target
     * @return an iterator
     * code generated using velocity template JPredicate_11.vm
    */
    static ResourceIterator<hasDriversTrainingFromSchool> hasDriversTrainingFromSchool_1(
        final example.nz.org.take.compiler.userv.domainmodel.Driver slot1,
        final DerivationController _derivation) {
        _derivation.log("public boolean example.nz.org.take.compiler.userv.domainmodel.Driver.hasDriversTrainingFromSchool()",
            DerivationController.JAVA_METHOD);

        if (slot1.hasDriversTrainingFromSchool()) {
            hasDriversTrainingFromSchool result = new hasDriversTrainingFromSchool();
            result.slot1 = slot1;

            return new SingletonIterator<hasDriversTrainingFromSchool>(result);
        }

        return EmptyIterator.DEFAULT;
    }
}
