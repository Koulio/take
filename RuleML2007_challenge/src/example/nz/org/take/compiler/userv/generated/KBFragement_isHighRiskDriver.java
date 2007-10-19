package example.nz.org.take.compiler.userv.generated;

import nz.org.take.rt.*;


/**
 * Class generated by the take compiler.
 * @version Fri Sep 21 11:43:27 NZST 2007
 */
@SuppressWarnings("unchecked")
class KBFragement_isHighRiskDriver {
    /**
     * Method generated for query /isHighRiskDriver[in]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of HighRiskDriver
    */
    public static ResultSet<HighRiskDriver> isHighRiskDriver(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver) {
        DerivationController _derivation = new DefaultDerivationController();
        ResultSet<HighRiskDriver> _result = new ResultSet(KBFragement_isHighRiskDriver.isHighRiskDriver(
                    driver, _derivation), _derivation);

        return _result;
    }

    /**
     * Method generated for query /isHighRiskDriver[in]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of HighRiskDriver
    */
    static ResourceIterator<HighRiskDriver> isHighRiskDriver(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        final int _derivationlevel = _derivation.getDepth();
        ResourceIterator<HighRiskDriver> result = new IteratorChain<HighRiskDriver>(3) {
                public Object getIteratorOrObject(int pos) {
                    switch (pos) {
                    // DE_DRC01  IF hasBeenConvictedOfaDUI(<driver>) THEN /isHighRiskDriver(<driver>)
                    case 0:
                        return isHighRiskDriver_0(driver,
                            _derivation.reset(_derivationlevel));

                    // DE_DRC02  IF greater_than_ii(getNumberOfAccidentsInvolvedIn(<driver>),2) THEN /isHighRiskDriver(<driver>)
                    case 1:
                        return isHighRiskDriver_1(driver,
                            _derivation.reset(_derivationlevel));

                    // DE_DRC03  IF greater_than_ii(getNumberOfMovingViolationsInLastTwoYears(<driver>),3) THEN /isHighRiskDriver(<driver>)
                    case 2:
                        return isHighRiskDriver_2(driver,
                            _derivation.reset(_derivationlevel));

                    default:
                        return EmptyIterator.DEFAULT;
                    } // switch(pos)
                } // getIterator()

                public String getRuleRef(int pos) {
                    switch (pos) {
                    // DE_DRC01  IF hasBeenConvictedOfaDUI(<driver>) THEN /isHighRiskDriver(<driver>)
                    case 0:
                        return "DE_DRC01";

                    // DE_DRC02  IF greater_than_ii(getNumberOfAccidentsInvolvedIn(<driver>),2) THEN /isHighRiskDriver(<driver>)
                    case 1:
                        return "DE_DRC02";

                    // DE_DRC03  IF greater_than_ii(getNumberOfMovingViolationsInLastTwoYears(<driver>),3) THEN /isHighRiskDriver(<driver>)
                    case 2:
                        return "DE_DRC03";

                    default:
                        return "";
                    } // switch(pos)
                } // getRuleRef()
            };

        return result;
    }

    /**
     * Method generated for query /isHighRiskDriver[in]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of HighRiskDriver
    */
    private static ResourceIterator<HighRiskDriver> isHighRiskDriver_0(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("DE_DRC01", DerivationController.RULE, driver);

        // Variable bindings in rule:  IF hasBeenConvictedOfaDUI(<driver>) THEN /isHighRiskDriver(<driver>)
        class bindingsInRule21 {
            // Property generated for term  "<driver>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p1;
        }
        ;

        final bindingsInRule21 bindings = new bindingsInRule21();
        bindings.p1 = driver;

        // code for prereq hasBeenConvictedOfaDUI(<driver>)
        final ResourceIterator<hasBeenConvictedOfaDUI> iterator1 = KBFragement_hasBeenConvictedOfaDUI_1.hasBeenConvictedOfaDUI_1(driver,
                _derivation.increaseDepth());

        // code for prereq /isHighRiskDriver(<driver>)
        final ResourceIterator<HighRiskDriver> iterator2 = new NestedIterator<hasBeenConvictedOfaDUI, HighRiskDriver>(iterator1) {
                public ResourceIterator<HighRiskDriver> getNextIterator(
                    hasBeenConvictedOfaDUI object) {
                    bindings.p1 = object.slot1;

                    return new SingletonIterator(new HighRiskDriver(bindings.p1));
                }
            };

        return iterator2;
    }

    /**
     * Method generated for query /isHighRiskDriver[in]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of HighRiskDriver
    */
    private static ResourceIterator<HighRiskDriver> isHighRiskDriver_1(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("DE_DRC02", DerivationController.RULE, driver);

        // Variable bindings in rule:  IF greater_than_ii(getNumberOfAccidentsInvolvedIn(<driver>),2) THEN /isHighRiskDriver(<driver>)
        class bindingsInRule22 {
            // Property generated for term  "<driver>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p1;

            // Property generated for term  "getNumberOfAccidentsInvolvedIn(<driver>)"
            int p2;

            // Property generated for term  "2"
            java.lang.Integer p3;
        }
        ;

        final bindingsInRule22 bindings = new bindingsInRule22();
        bindings.p1 = driver;
        bindings.p2 = driver.getNumberOfAccidentsInvolvedIn();
        bindings.p3 = 2;

        // code for prereq greater_than_ii(getNumberOfAccidentsInvolvedIn(<driver>),2)
        final ResourceIterator<greater_than_ii> iterator1 = KBFragement_greater_than_ii_11.greater_than_ii_11(bindings.p2,
                2, _derivation.increaseDepth());

        // code for prereq /isHighRiskDriver(<driver>)
        final ResourceIterator<HighRiskDriver> iterator2 = new NestedIterator<greater_than_ii, HighRiskDriver>(iterator1) {
                public ResourceIterator<HighRiskDriver> getNextIterator(
                    greater_than_ii object) {
                    bindings.p2 = object.slot1;
                    bindings.p3 = 2;

                    return new SingletonIterator(new HighRiskDriver(bindings.p1));
                }
            };

        return iterator2;
    }

    /**
     * Method generated for query /isHighRiskDriver[in]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of HighRiskDriver
    */
    private static ResourceIterator<HighRiskDriver> isHighRiskDriver_2(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("DE_DRC03", DerivationController.RULE, driver);

        // Variable bindings in rule:  IF greater_than_ii(getNumberOfMovingViolationsInLastTwoYears(<driver>),3) THEN /isHighRiskDriver(<driver>)
        class bindingsInRule23 {
            // Property generated for term  "3"
            java.lang.Integer p1;

            // Property generated for term  "<driver>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p2;

            // Property generated for term  "getNumberOfMovingViolationsInLastTwoYears(<driver>)"
            int p3;
        }
        ;

        final bindingsInRule23 bindings = new bindingsInRule23();
        bindings.p2 = driver;
        bindings.p1 = 3;
        bindings.p3 = driver.getNumberOfMovingViolationsInLastTwoYears();

        // code for prereq greater_than_ii(getNumberOfMovingViolationsInLastTwoYears(<driver>),3)
        final ResourceIterator<greater_than_ii> iterator1 = KBFragement_greater_than_ii_11.greater_than_ii_11(bindings.p3,
                3, _derivation.increaseDepth());

        // code for prereq /isHighRiskDriver(<driver>)
        final ResourceIterator<HighRiskDriver> iterator2 = new NestedIterator<greater_than_ii, HighRiskDriver>(iterator1) {
                public ResourceIterator<HighRiskDriver> getNextIterator(
                    greater_than_ii object) {
                    bindings.p3 = object.slot1;
                    bindings.p1 = 3;

                    return new SingletonIterator(new HighRiskDriver(bindings.p2));
                }
            };

        return iterator2;
    }
}
