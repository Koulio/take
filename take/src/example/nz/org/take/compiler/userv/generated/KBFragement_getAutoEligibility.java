package example.nz.org.take.compiler.userv.generated;

import nz.org.take.rt.*;


/**
 * Class generated by the take compiler.
 * @version Mon Aug 20 14:38:14 NZST 2007
 */
@SuppressWarnings("unchecked")
class KBFragement_getAutoEligibility {
    /**
     * Method generated for query /autoEligibility[in,out]
     * @param car input parameter generated from slot 0
     * @return an iterator for instances of AutoEligibility
    */
    public static ResultSet<AutoEligibility> getAutoEligibility(
        final example.nz.org.take.compiler.userv.domainmodel.Car car) {
        DerivationController _derivation = new DefaultDerivationController();
        ResultSet<AutoEligibility> _result = new ResultSet(KBFragement_getAutoEligibility.getAutoEligibility(
                    car, _derivation), _derivation);

        return _result;
    }

    /**
     * Method generated for query /autoEligibility[in,out]
     * @param car input parameter generated from slot 0
     * @return an iterator for instances of AutoEligibility
    */
    static ResourceIterator<AutoEligibility> getAutoEligibility(
        final example.nz.org.take.compiler.userv.domainmodel.Car car,
        final DerivationController _derivation) {
        final int _derivationlevel = _derivation.getDepth();
        ResourceIterator<AutoEligibility> result = new IteratorChain<AutoEligibility>(4) {
                public Object getIteratorOrObject(int pos) {
                    switch (pos) {
                    //  IF /potentialOccupantInjuryRating(<car>,extremely high) THEN /autoEligibility(<car>,not eligible)
                    case 0:
                        return getAutoEligibility_0(car,
                            _derivation.reset(_derivationlevel));

                    //  IF /potentialOccupantInjuryRating(<car>,high) THEN /autoEligibility(<car>,provisional)
                    case 1:
                        return getAutoEligibility_1(car,
                            _derivation.reset(_derivationlevel));

                    //  IF /potentialTheftRating(<car>,high) THEN /autoEligibility(<car>,provisional)
                    case 2:
                        return getAutoEligibility_2(car,
                            _derivation.reset(_derivationlevel));

                    //  IF  THEN /autoEligibility(<car>,eligible)
                    case 3:
                        return getAutoEligibility_3(car,
                            _derivation.reset(_derivationlevel));

                    default:
                        return EmptyIterator.DEFAULT;
                    } // switch(pos)
                } // getIterator()

                public String getRuleRef(int pos) {
                    switch (pos) {
                    //  IF /potentialOccupantInjuryRating(<car>,extremely high) THEN /autoEligibility(<car>,not eligible)
                    case 0:
                        return "AE_01";

                    //  IF /potentialOccupantInjuryRating(<car>,high) THEN /autoEligibility(<car>,provisional)
                    case 1:
                        return "AE_02";

                    //  IF /potentialTheftRating(<car>,high) THEN /autoEligibility(<car>,provisional)
                    case 2:
                        return "AE_03";

                    //  IF  THEN /autoEligibility(<car>,eligible)
                    case 3:
                        return "AE_04";

                    default:
                        return "";
                    } // switch(pos)
                } // getRuleRef()
            };

        return result;
    }

    /**
     * Method generated for query /autoEligibility[in,out]
     * @param car input parameter generated from slot 0
     * @return an iterator for instances of AutoEligibility
    */
    private static ResourceIterator<AutoEligibility> getAutoEligibility_0(
        final example.nz.org.take.compiler.userv.domainmodel.Car car,
        final DerivationController _derivation) {
        _derivation.log("AE_01", DerivationController.RULE, car,
            DerivationController.NIL);

        // Variable bindings in rule:  IF /potentialOccupantInjuryRating(<car>,extremely high) THEN /autoEligibility(<car>,not eligible)
        class bindingsInRule11 {
            // Property generated for term  "<car>"
            example.nz.org.take.compiler.userv.domainmodel.Car p1;

            // Property generated for term  "extremely high"
            java.lang.String p2;

            // Property generated for term  "not eligible"
            java.lang.String p3;
        }
        ;

        final bindingsInRule11 bindings = new bindingsInRule11();
        bindings.p2 = "extremely high";
        bindings.p3 = "not eligible";
        bindings.p1 = car;

        // code for prereq /potentialOccupantInjuryRating(<car>,extremely high)
        final ResourceIterator<PotentialOccupantInjuryRating> iterator1 = KBFragement_potentialOccupantInjuryRating_11.potentialOccupantInjuryRating_11(car,
                "extremely high", _derivation.increaseDepth());

        // code for prereq /autoEligibility(<car>,not eligible)
        final ResourceIterator<AutoEligibility> iterator2 = new NestedIterator<PotentialOccupantInjuryRating, AutoEligibility>(iterator1) {
                public ResourceIterator<AutoEligibility> getNextIterator(
                    PotentialOccupantInjuryRating object) {
                    bindings.p1 = object.car;
                    bindings.p2 = "extremely high";

                    return new SingletonIterator(new AutoEligibility(
                            bindings.p1, bindings.p3));
                }
            };

        return iterator2;
    }

    /**
     * Method generated for query /autoEligibility[in,out]
     * @param car input parameter generated from slot 0
     * @return an iterator for instances of AutoEligibility
    */
    private static ResourceIterator<AutoEligibility> getAutoEligibility_1(
        final example.nz.org.take.compiler.userv.domainmodel.Car car,
        final DerivationController _derivation) {
        _derivation.log("AE_02", DerivationController.RULE, car,
            DerivationController.NIL);

        // Variable bindings in rule:  IF /potentialOccupantInjuryRating(<car>,high) THEN /autoEligibility(<car>,provisional)
        class bindingsInRule12 {
            // Property generated for term  "<car>"
            example.nz.org.take.compiler.userv.domainmodel.Car p1;

            // Property generated for term  "high"
            java.lang.String p2;

            // Property generated for term  "provisional"
            java.lang.String p3;
        }
        ;

        final bindingsInRule12 bindings = new bindingsInRule12();
        bindings.p2 = "high";
        bindings.p1 = car;
        bindings.p3 = "provisional";

        // code for prereq /potentialOccupantInjuryRating(<car>,high)
        final ResourceIterator<PotentialOccupantInjuryRating> iterator1 = KBFragement_potentialOccupantInjuryRating_11.potentialOccupantInjuryRating_11(car,
                "high", _derivation.increaseDepth());

        // code for prereq /autoEligibility(<car>,provisional)
        final ResourceIterator<AutoEligibility> iterator2 = new NestedIterator<PotentialOccupantInjuryRating, AutoEligibility>(iterator1) {
                public ResourceIterator<AutoEligibility> getNextIterator(
                    PotentialOccupantInjuryRating object) {
                    bindings.p1 = object.car;
                    bindings.p2 = "high";

                    return new SingletonIterator(new AutoEligibility(
                            bindings.p1, bindings.p3));
                }
            };

        return iterator2;
    }

    /**
     * Method generated for query /autoEligibility[in,out]
     * @param car input parameter generated from slot 0
     * @return an iterator for instances of AutoEligibility
    */
    private static ResourceIterator<AutoEligibility> getAutoEligibility_2(
        final example.nz.org.take.compiler.userv.domainmodel.Car car,
        final DerivationController _derivation) {
        _derivation.log("AE_03", DerivationController.RULE, car,
            DerivationController.NIL);

        // Variable bindings in rule:  IF /potentialTheftRating(<car>,high) THEN /autoEligibility(<car>,provisional)
        class bindingsInRule13 {
            // Property generated for term  "<car>"
            example.nz.org.take.compiler.userv.domainmodel.Car p1;

            // Property generated for term  "high"
            java.lang.String p2;

            // Property generated for term  "provisional"
            java.lang.String p3;
        }
        ;

        final bindingsInRule13 bindings = new bindingsInRule13();
        bindings.p2 = "high";
        bindings.p1 = car;
        bindings.p3 = "provisional";

        // code for prereq /potentialTheftRating(<car>,high)
        final ResourceIterator<PotentialTheftRating> iterator1 = KBFragement_potentialTheftRating_11.potentialTheftRating_11(car,
                "high", _derivation.increaseDepth());

        // code for prereq /autoEligibility(<car>,provisional)
        final ResourceIterator<AutoEligibility> iterator2 = new NestedIterator<PotentialTheftRating, AutoEligibility>(iterator1) {
                public ResourceIterator<AutoEligibility> getNextIterator(
                    PotentialTheftRating object) {
                    bindings.p1 = object.car;
                    bindings.p2 = "high";

                    return new SingletonIterator(new AutoEligibility(
                            bindings.p1, bindings.p3));
                }
            };

        return iterator2;
    }

    /**
     * Method generated for query /autoEligibility[in,out]
     * @param car input parameter generated from slot 0
     * @return an iterator for instances of AutoEligibility
    */
    private static ResourceIterator<AutoEligibility> getAutoEligibility_3(
        final example.nz.org.take.compiler.userv.domainmodel.Car car,
        final DerivationController _derivation) {
        _derivation.log("AE_04", DerivationController.RULE, car,
            DerivationController.NIL);

        // rule with empty body
        AutoEligibility result = new AutoEligibility();
        result.car = car;
        result.value = "eligible";

        return new SingletonIterator<AutoEligibility>(result);
    }
}
