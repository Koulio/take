package example.nz.org.take.compiler.userv.generated;

import nz.org.take.rt.*;


/**
 * Class generated by the take compiler.
 * @version Wed Sep 19 21:51:56 NZST 2007
 */
@SuppressWarnings("unchecked")
class KBFragement_hasTrainingCertification {
    /**
     * Method generated for query /hasTrainingCertification[in]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of TrainedDriver
    */
    public static ResultSet<TrainedDriver> hasTrainingCertification(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver) {
        DerivationController _derivation = new DefaultDerivationController();
        ResultSet<TrainedDriver> _result = new ResultSet(KBFragement_hasTrainingCertification.hasTrainingCertification(
                    driver, _derivation), _derivation);

        return _result;
    }

    /**
     * Method generated for query /hasTrainingCertification[in]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of TrainedDriver
    */
    static ResourceIterator<TrainedDriver> hasTrainingCertification(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        final int _derivationlevel = _derivation.getDepth();
        ResourceIterator<TrainedDriver> result = new IteratorChain<TrainedDriver>(3) {
                public Object getIteratorOrObject(int pos) {
                    switch (pos) {
                    //  IF hasDriversTrainingFromSchool(<driver>) THEN /hasTrainingCertification(<driver>)
                    case 0:
                        return hasTrainingCertification_0(driver,
                            _derivation.reset(_derivationlevel));

                    //  IF hasDriversTrainingFromLicensedDriverTrainingCompany(<driver>) THEN /hasTrainingCertification(<driver>)
                    case 1:
                        return hasTrainingCertification_1(driver,
                            _derivation.reset(_derivationlevel));

                    //  IF hasTakenASeniorCitizenDriversRefresherCourse(<driver>) THEN /hasTrainingCertification(<driver>)
                    case 2:
                        return hasTrainingCertification_2(driver,
                            _derivation.reset(_derivationlevel));

                    default:
                        return EmptyIterator.DEFAULT;
                    } // switch(pos)
                } // getIterator()

                public String getRuleRef(int pos) {
                    switch (pos) {
                    //  IF hasDriversTrainingFromSchool(<driver>) THEN /hasTrainingCertification(<driver>)
                    case 0:
                        return "DE_DAC07";

                    //  IF hasDriversTrainingFromLicensedDriverTrainingCompany(<driver>) THEN /hasTrainingCertification(<driver>)
                    case 1:
                        return "DE_DAC08";

                    //  IF hasTakenASeniorCitizenDriversRefresherCourse(<driver>) THEN /hasTrainingCertification(<driver>)
                    case 2:
                        return "DE_DAC09";

                    default:
                        return "";
                    } // switch(pos)
                } // getRuleRef()
            };

        return result;
    }

    /**
     * Method generated for query /hasTrainingCertification[in]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of TrainedDriver
    */
    private static ResourceIterator<TrainedDriver> hasTrainingCertification_0(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("DE_DAC07", DerivationController.RULE, driver);

        // Variable bindings in rule:  IF hasDriversTrainingFromSchool(<driver>) THEN /hasTrainingCertification(<driver>)
        class bindingsInRule24 {
            // Property generated for term  "<driver>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p1;
        }
        ;

        final bindingsInRule24 bindings = new bindingsInRule24();
        bindings.p1 = driver;

        // code for prereq hasDriversTrainingFromSchool(<driver>)
        final ResourceIterator<hasDriversTrainingFromSchool> iterator1 = KBFragement_hasDriversTrainingFromSchool_1.hasDriversTrainingFromSchool_1(driver,
                _derivation.increaseDepth());

        // code for prereq /hasTrainingCertification(<driver>)
        final ResourceIterator<TrainedDriver> iterator2 = new NestedIterator<hasDriversTrainingFromSchool, TrainedDriver>(iterator1) {
                public ResourceIterator<TrainedDriver> getNextIterator(
                    hasDriversTrainingFromSchool object) {
                    bindings.p1 = object.slot1;

                    return new SingletonIterator(new TrainedDriver(bindings.p1));
                }
            };

        return iterator2;
    }

    /**
     * Method generated for query /hasTrainingCertification[in]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of TrainedDriver
    */
    private static ResourceIterator<TrainedDriver> hasTrainingCertification_1(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("DE_DAC08", DerivationController.RULE, driver);

        // Variable bindings in rule:  IF hasDriversTrainingFromLicensedDriverTrainingCompany(<driver>) THEN /hasTrainingCertification(<driver>)
        class bindingsInRule25 {
            // Property generated for term  "<driver>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p1;
        }
        ;

        final bindingsInRule25 bindings = new bindingsInRule25();
        bindings.p1 = driver;

        // code for prereq hasDriversTrainingFromLicensedDriverTrainingCompany(<driver>)
        final ResourceIterator<hasDriversTrainingFromLicensedDriverTrainingCompany> iterator1 =
            KBFragement_hasDriversTrainingFromLicensedDriverTrainingCompany_1.hasDriversTrainingFromLicensedDriverTrainingCompany_1(driver,
                _derivation.increaseDepth());

        // code for prereq /hasTrainingCertification(<driver>)
        final ResourceIterator<TrainedDriver> iterator2 = new NestedIterator<hasDriversTrainingFromLicensedDriverTrainingCompany, TrainedDriver>(iterator1) {
                public ResourceIterator<TrainedDriver> getNextIterator(
                    hasDriversTrainingFromLicensedDriverTrainingCompany object) {
                    bindings.p1 = object.slot1;

                    return new SingletonIterator(new TrainedDriver(bindings.p1));
                }
            };

        return iterator2;
    }

    /**
     * Method generated for query /hasTrainingCertification[in]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of TrainedDriver
    */
    private static ResourceIterator<TrainedDriver> hasTrainingCertification_2(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("DE_DAC09", DerivationController.RULE, driver);

        // Variable bindings in rule:  IF hasTakenASeniorCitizenDriversRefresherCourse(<driver>) THEN /hasTrainingCertification(<driver>)
        class bindingsInRule26 {
            // Property generated for term  "<driver>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p1;
        }
        ;

        final bindingsInRule26 bindings = new bindingsInRule26();
        bindings.p1 = driver;

        // code for prereq hasTakenASeniorCitizenDriversRefresherCourse(<driver>)
        final ResourceIterator<hasTakenASeniorCitizenDriversRefresherCourse> iterator1 =
            KBFragement_hasTakenASeniorCitizenDriversRefresherCourse_1.hasTakenASeniorCitizenDriversRefresherCourse_1(driver,
                _derivation.increaseDepth());

        // code for prereq /hasTrainingCertification(<driver>)
        final ResourceIterator<TrainedDriver> iterator2 = new NestedIterator<hasTakenASeniorCitizenDriversRefresherCourse, TrainedDriver>(iterator1) {
                public ResourceIterator<TrainedDriver> getNextIterator(
                    hasTakenASeniorCitizenDriversRefresherCourse object) {
                    bindings.p1 = object.slot1;

                    return new SingletonIterator(new TrainedDriver(bindings.p1));
                }
            };

        return iterator2;
    }
}
