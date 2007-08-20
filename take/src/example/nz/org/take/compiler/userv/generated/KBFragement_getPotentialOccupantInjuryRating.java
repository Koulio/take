package example.nz.org.take.compiler.userv.generated;

import nz.org.take.rt.*;


/**
 * Class generated by the take compiler.
 * @version Mon Aug 20 14:38:14 NZST 2007
 */
@SuppressWarnings("unchecked")
class KBFragement_getPotentialOccupantInjuryRating {
    /**
     * Method generated for query /potentialOccupantInjuryRating[in,out]
     * @param car input parameter generated from slot 0
     * @return an iterator for instances of PotentialOccupantInjuryRating
    */
    public static ResultSet<PotentialOccupantInjuryRating> getPotentialOccupantInjuryRating(
        final example.nz.org.take.compiler.userv.domainmodel.Car car) {
        DerivationController _derivation = new DefaultDerivationController();
        ResultSet<PotentialOccupantInjuryRating> _result = new ResultSet(KBFragement_getPotentialOccupantInjuryRating.getPotentialOccupantInjuryRating(
                    car, _derivation), _derivation);

        return _result;
    }

    /**
     * Method generated for query /potentialOccupantInjuryRating[in,out]
     * @param car input parameter generated from slot 0
     * @return an iterator for instances of PotentialOccupantInjuryRating
    */
    static ResourceIterator<PotentialOccupantInjuryRating> getPotentialOccupantInjuryRating(
        final example.nz.org.take.compiler.userv.domainmodel.Car car,
        final DerivationController _derivation) {
        final int _derivationlevel = _derivation.getDepth();
        ResourceIterator<PotentialOccupantInjuryRating> result = new IteratorChain<PotentialOccupantInjuryRating>(5) {
                public Object getIteratorOrObject(int pos) {
                    switch (pos) {
                    //  IF hasAirbags(<car>) THEN /potentialOccupantInjuryRating(<car>,extremely high)
                    case 0:
                        return getPotentialOccupantInjuryRating_0(car,
                            _derivation.reset(_derivationlevel));

                    //  IF hasDriversAirbag(<car>) AND hasFrontPassengerAirbag(<car>) AND hasSidePanelAirbags(<car>) THEN /potentialOccupantInjuryRating(<car>,high)
                    case 1:
                        return getPotentialOccupantInjuryRating_1(car,
                            _derivation.reset(_derivationlevel));

                    //  IF hasDriversAirbag(<car>) AND hasFrontPassengerAirbag(<car>) AND hasSidePanelAirbags(<car>) THEN /potentialOccupantInjuryRating(<car>,moderate)
                    case 2:
                        return getPotentialOccupantInjuryRating_2(car,
                            _derivation.reset(_derivationlevel));

                    //  IF hasDriversAirbag(<car>) AND hasFrontPassengerAirbag(<car>) AND hasSidePanelAirbags(<car>) THEN /potentialOccupantInjuryRating(<car>,low)
                    case 3:
                        return getPotentialOccupantInjuryRating_3(car,
                            _derivation.reset(_derivationlevel));

                    //  IF isConvertible(<car>) AND /hasHasRollBar(<car>) THEN /potentialOccupantInjuryRating(<car>,extremely high)
                    case 4:
                        return getPotentialOccupantInjuryRating_4(car,
                            _derivation.reset(_derivationlevel));

                    default:
                        return EmptyIterator.DEFAULT;
                    } // switch(pos)
                } // getIterator()

                public String getRuleRef(int pos) {
                    switch (pos) {
                    //  IF hasAirbags(<car>) THEN /potentialOccupantInjuryRating(<car>,extremely high)
                    case 0:
                        return "AE_POIC01";

                    //  IF hasDriversAirbag(<car>) AND hasFrontPassengerAirbag(<car>) AND hasSidePanelAirbags(<car>) THEN /potentialOccupantInjuryRating(<car>,high)
                    case 1:
                        return "AE_POIC02";

                    //  IF hasDriversAirbag(<car>) AND hasFrontPassengerAirbag(<car>) AND hasSidePanelAirbags(<car>) THEN /potentialOccupantInjuryRating(<car>,moderate)
                    case 2:
                        return "AE_POIC03";

                    //  IF hasDriversAirbag(<car>) AND hasFrontPassengerAirbag(<car>) AND hasSidePanelAirbags(<car>) THEN /potentialOccupantInjuryRating(<car>,low)
                    case 3:
                        return "AE_POIC04";

                    //  IF isConvertible(<car>) AND /hasHasRollBar(<car>) THEN /potentialOccupantInjuryRating(<car>,extremely high)
                    case 4:
                        return "AE_POIC05";

                    default:
                        return "";
                    } // switch(pos)
                } // getRuleRef()
            };

        return result;
    }

    /**
     * Method generated for query /potentialOccupantInjuryRating[in,out]
     * @param car input parameter generated from slot 0
     * @return an iterator for instances of PotentialOccupantInjuryRating
    */
    private static ResourceIterator<PotentialOccupantInjuryRating> getPotentialOccupantInjuryRating_0(
        final example.nz.org.take.compiler.userv.domainmodel.Car car,
        final DerivationController _derivation) {
        _derivation.log("AE_POIC01", DerivationController.RULE, car,
            DerivationController.NIL);

        // Variable bindings in rule:  IF hasAirbags(<car>) THEN /potentialOccupantInjuryRating(<car>,extremely high)
        class bindingsInRule6 {
            // Property generated for term  "<car>"
            example.nz.org.take.compiler.userv.domainmodel.Car p1;

            // Property generated for term  "extremely high"
            java.lang.String p2;
        }
        ;

        final bindingsInRule6 bindings = new bindingsInRule6();
        bindings.p2 = "extremely high";
        bindings.p1 = car;

        // code for prereq hasAirbags(<car>)
        final ResourceIterator<_hasAirbags> iterator1 = KBFragement_neg_hasAirbags_1.neg_hasAirbags_1(car,
                _derivation.increaseDepth());

        // code for prereq /potentialOccupantInjuryRating(<car>,extremely high)
        final ResourceIterator<PotentialOccupantInjuryRating> iterator2 = new NestedIterator<_hasAirbags, PotentialOccupantInjuryRating>(iterator1) {
                public ResourceIterator<PotentialOccupantInjuryRating> getNextIterator(
                    _hasAirbags object) {
                    bindings.p1 = object.slot1;

                    return new SingletonIterator(new PotentialOccupantInjuryRating(
                            bindings.p1, bindings.p2));
                }
            };

        return iterator2;
    }

    /**
     * Method generated for query /potentialOccupantInjuryRating[in,out]
     * @param car input parameter generated from slot 0
     * @return an iterator for instances of PotentialOccupantInjuryRating
    */
    private static ResourceIterator<PotentialOccupantInjuryRating> getPotentialOccupantInjuryRating_1(
        final example.nz.org.take.compiler.userv.domainmodel.Car car,
        final DerivationController _derivation) {
        _derivation.log("AE_POIC02", DerivationController.RULE, car,
            DerivationController.NIL);

        // Variable bindings in rule:  IF hasDriversAirbag(<car>) AND hasFrontPassengerAirbag(<car>) AND hasSidePanelAirbags(<car>) THEN /potentialOccupantInjuryRating(<car>,high)
        class bindingsInRule7 {
            // Property generated for term  "<car>"
            example.nz.org.take.compiler.userv.domainmodel.Car p1;

            // Property generated for term  "high"
            java.lang.String p2;
        }
        ;

        final bindingsInRule7 bindings = new bindingsInRule7();
        bindings.p2 = "high";
        bindings.p1 = car;

        // code for prereq hasDriversAirbag(<car>)
        final ResourceIterator<_hasDriversAirbag> iterator1 = KBFragement_hasDriversAirbag_1.hasDriversAirbag_1(car,
                _derivation.increaseDepth());

        // code for prereq hasFrontPassengerAirbag(<car>)
        final ResourceIterator<_hasFrontPassengerAirbag> iterator2 = new NestedIterator<_hasDriversAirbag, _hasFrontPassengerAirbag>(iterator1) {
                public ResourceIterator<_hasFrontPassengerAirbag> getNextIterator(
                    _hasDriversAirbag object) {
                    bindings.p1 = object.slot1;

                    return KBFragement_neg_hasFrontPassengerAirbag_1.neg_hasFrontPassengerAirbag_1(bindings.p1,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq hasSidePanelAirbags(<car>)
        final ResourceIterator<_hasSidePanelAirbags> iterator3 = new NestedIterator<_hasFrontPassengerAirbag, _hasSidePanelAirbags>(iterator2) {
                public ResourceIterator<_hasSidePanelAirbags> getNextIterator(
                    _hasFrontPassengerAirbag object) {
                    bindings.p1 = object.slot1;

                    return KBFragement_neg_hasSidePanelAirbags_1.neg_hasSidePanelAirbags_1(bindings.p1,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq /potentialOccupantInjuryRating(<car>,high)
        final ResourceIterator<PotentialOccupantInjuryRating> iterator4 = new NestedIterator<_hasSidePanelAirbags, PotentialOccupantInjuryRating>(iterator3) {
                public ResourceIterator<PotentialOccupantInjuryRating> getNextIterator(
                    _hasSidePanelAirbags object) {
                    bindings.p1 = object.slot1;

                    return new SingletonIterator(new PotentialOccupantInjuryRating(
                            bindings.p1, bindings.p2));
                }
            };

        return iterator4;
    }

    /**
     * Method generated for query /potentialOccupantInjuryRating[in,out]
     * @param car input parameter generated from slot 0
     * @return an iterator for instances of PotentialOccupantInjuryRating
    */
    private static ResourceIterator<PotentialOccupantInjuryRating> getPotentialOccupantInjuryRating_2(
        final example.nz.org.take.compiler.userv.domainmodel.Car car,
        final DerivationController _derivation) {
        _derivation.log("AE_POIC03", DerivationController.RULE, car,
            DerivationController.NIL);

        // Variable bindings in rule:  IF hasDriversAirbag(<car>) AND hasFrontPassengerAirbag(<car>) AND hasSidePanelAirbags(<car>) THEN /potentialOccupantInjuryRating(<car>,moderate)
        class bindingsInRule8 {
            // Property generated for term  "<car>"
            example.nz.org.take.compiler.userv.domainmodel.Car p1;

            // Property generated for term  "moderate"
            java.lang.String p2;
        }
        ;

        final bindingsInRule8 bindings = new bindingsInRule8();
        bindings.p2 = "moderate";
        bindings.p1 = car;

        // code for prereq hasDriversAirbag(<car>)
        final ResourceIterator<_hasDriversAirbag> iterator1 = KBFragement_hasDriversAirbag_1.hasDriversAirbag_1(car,
                _derivation.increaseDepth());

        // code for prereq hasFrontPassengerAirbag(<car>)
        final ResourceIterator<_hasFrontPassengerAirbag> iterator2 = new NestedIterator<_hasDriversAirbag, _hasFrontPassengerAirbag>(iterator1) {
                public ResourceIterator<_hasFrontPassengerAirbag> getNextIterator(
                    _hasDriversAirbag object) {
                    bindings.p1 = object.slot1;

                    return KBFragement_hasFrontPassengerAirbag_1.hasFrontPassengerAirbag_1(bindings.p1,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq hasSidePanelAirbags(<car>)
        final ResourceIterator<_hasSidePanelAirbags> iterator3 = new NestedIterator<_hasFrontPassengerAirbag, _hasSidePanelAirbags>(iterator2) {
                public ResourceIterator<_hasSidePanelAirbags> getNextIterator(
                    _hasFrontPassengerAirbag object) {
                    bindings.p1 = object.slot1;

                    return KBFragement_neg_hasSidePanelAirbags_1.neg_hasSidePanelAirbags_1(bindings.p1,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq /potentialOccupantInjuryRating(<car>,moderate)
        final ResourceIterator<PotentialOccupantInjuryRating> iterator4 = new NestedIterator<_hasSidePanelAirbags, PotentialOccupantInjuryRating>(iterator3) {
                public ResourceIterator<PotentialOccupantInjuryRating> getNextIterator(
                    _hasSidePanelAirbags object) {
                    bindings.p1 = object.slot1;

                    return new SingletonIterator(new PotentialOccupantInjuryRating(
                            bindings.p1, bindings.p2));
                }
            };

        return iterator4;
    }

    /**
     * Method generated for query /potentialOccupantInjuryRating[in,out]
     * @param car input parameter generated from slot 0
     * @return an iterator for instances of PotentialOccupantInjuryRating
    */
    private static ResourceIterator<PotentialOccupantInjuryRating> getPotentialOccupantInjuryRating_3(
        final example.nz.org.take.compiler.userv.domainmodel.Car car,
        final DerivationController _derivation) {
        _derivation.log("AE_POIC04", DerivationController.RULE, car,
            DerivationController.NIL);

        // Variable bindings in rule:  IF hasDriversAirbag(<car>) AND hasFrontPassengerAirbag(<car>) AND hasSidePanelAirbags(<car>) THEN /potentialOccupantInjuryRating(<car>,low)
        class bindingsInRule9 {
            // Property generated for term  "<car>"
            example.nz.org.take.compiler.userv.domainmodel.Car p1;

            // Property generated for term  "low"
            java.lang.String p2;
        }
        ;

        final bindingsInRule9 bindings = new bindingsInRule9();
        bindings.p2 = "low";
        bindings.p1 = car;

        // code for prereq hasDriversAirbag(<car>)
        final ResourceIterator<_hasDriversAirbag> iterator1 = KBFragement_hasDriversAirbag_1.hasDriversAirbag_1(car,
                _derivation.increaseDepth());

        // code for prereq hasFrontPassengerAirbag(<car>)
        final ResourceIterator<_hasFrontPassengerAirbag> iterator2 = new NestedIterator<_hasDriversAirbag, _hasFrontPassengerAirbag>(iterator1) {
                public ResourceIterator<_hasFrontPassengerAirbag> getNextIterator(
                    _hasDriversAirbag object) {
                    bindings.p1 = object.slot1;

                    return KBFragement_hasFrontPassengerAirbag_1.hasFrontPassengerAirbag_1(bindings.p1,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq hasSidePanelAirbags(<car>)
        final ResourceIterator<_hasSidePanelAirbags> iterator3 = new NestedIterator<_hasFrontPassengerAirbag, _hasSidePanelAirbags>(iterator2) {
                public ResourceIterator<_hasSidePanelAirbags> getNextIterator(
                    _hasFrontPassengerAirbag object) {
                    bindings.p1 = object.slot1;

                    return KBFragement_hasSidePanelAirbags_1.hasSidePanelAirbags_1(bindings.p1,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq /potentialOccupantInjuryRating(<car>,low)
        final ResourceIterator<PotentialOccupantInjuryRating> iterator4 = new NestedIterator<_hasSidePanelAirbags, PotentialOccupantInjuryRating>(iterator3) {
                public ResourceIterator<PotentialOccupantInjuryRating> getNextIterator(
                    _hasSidePanelAirbags object) {
                    bindings.p1 = object.slot1;

                    return new SingletonIterator(new PotentialOccupantInjuryRating(
                            bindings.p1, bindings.p2));
                }
            };

        return iterator4;
    }

    /**
     * Method generated for query /potentialOccupantInjuryRating[in,out]
     * @param car input parameter generated from slot 0
     * @return an iterator for instances of PotentialOccupantInjuryRating
    */
    private static ResourceIterator<PotentialOccupantInjuryRating> getPotentialOccupantInjuryRating_4(
        final example.nz.org.take.compiler.userv.domainmodel.Car car,
        final DerivationController _derivation) {
        _derivation.log("AE_POIC05", DerivationController.RULE, car,
            DerivationController.NIL);

        // Variable bindings in rule:  IF isConvertible(<car>) AND /hasHasRollBar(<car>) THEN /potentialOccupantInjuryRating(<car>,extremely high)
        class bindingsInRule10 {
            // Property generated for term  "<car>"
            example.nz.org.take.compiler.userv.domainmodel.Car p1;

            // Property generated for term  "extremely high"
            java.lang.String p2;
        }
        ;

        final bindingsInRule10 bindings = new bindingsInRule10();
        bindings.p2 = "extremely high";
        bindings.p1 = car;

        // code for prereq isConvertible(<car>)
        final ResourceIterator<_isConvertible> iterator1 = KBFragement_isConvertible_1.isConvertible_1(car,
                _derivation.increaseDepth());

        // code for prereq /hasHasRollBar(<car>)
        final ResourceIterator<_hasHasRollBar> iterator2 = new NestedIterator<_isConvertible, _hasHasRollBar>(iterator1) {
                public ResourceIterator<_hasHasRollBar> getNextIterator(
                    _isConvertible object) {
                    bindings.p1 = object.slot1;

                    return KBFragement_neg_hasHasRollBar_1.neg_hasHasRollBar_1(bindings.p1,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq /potentialOccupantInjuryRating(<car>,extremely high)
        final ResourceIterator<PotentialOccupantInjuryRating> iterator3 = new NestedIterator<_hasHasRollBar, PotentialOccupantInjuryRating>(iterator2) {
                public ResourceIterator<PotentialOccupantInjuryRating> getNextIterator(
                    _hasHasRollBar object) {
                    bindings.p1 = object.slot1;

                    return new SingletonIterator(new PotentialOccupantInjuryRating(
                            bindings.p1, bindings.p2));
                }
            };

        return iterator3;
    }
}
