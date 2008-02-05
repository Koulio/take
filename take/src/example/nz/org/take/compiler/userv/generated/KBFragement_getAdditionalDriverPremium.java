package example.nz.org.take.compiler.userv.generated;

import nz.org.take.rt.*;


/**
 * Class generated by the take compiler.
 * @version Tue Feb 05 14:53:50 NZDT 2008
 */
@SuppressWarnings("unchecked")
class KBFragement_getAdditionalDriverPremium {
    /**
     * Method generated for query /additionalDriverPremium[in,out]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of AdditionalDriverPremium
    */
    public static ResultSet<AdditionalDriverPremium> getAdditionalDriverPremium(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver) {
        DerivationController _derivation = new DefaultDerivationController();
        ResultSet<AdditionalDriverPremium> _result = new ResultSet(KBFragement_getAdditionalDriverPremium.getAdditionalDriverPremium(
                    driver, _derivation), _derivation);

        return _result;
    }

    /**
     * Method generated for query /additionalDriverPremium[in,out]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of AdditionalDriverPremium
    */
    static ResourceIterator<AdditionalDriverPremium> getAdditionalDriverPremium(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        final int _derivationlevel = _derivation.getDepth();
        ResourceIterator<AdditionalDriverPremium> result = new IteratorChain<AdditionalDriverPremium>(10) {
                public Object getIteratorOrObject(int pos) {
                    switch (pos) {
                    // DP_01  IF /driverCategory(<driver>,young driver) AND isMarried(<driver>) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,700)
                    case 0:
                        return getAdditionalDriverPremium_0(driver,
                            _derivation.reset(_derivationlevel));

                    // DP_02  IF /driverCategory(<driver>,young driver) AND isMarried(<driver>) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,720)
                    case 1:
                        return getAdditionalDriverPremium_1(driver,
                            _derivation.reset(_derivationlevel));

                    // DP_03  IF /driverCategory(<driver>,young driver) AND isMarried(<driver>) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,300)
                    case 2:
                        return getAdditionalDriverPremium_2(driver,
                            _derivation.reset(_derivationlevel));

                    // DP_04  IF /driverCategory(<driver>,young driver) AND isMarried(<driver>) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,300)
                    case 3:
                        return getAdditionalDriverPremium_3(driver,
                            _derivation.reset(_derivationlevel));

                    // DP_05  IF /driverCategory(<driver>,senior driver) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,500)
                    case 4:
                        return getAdditionalDriverPremium_4(driver,
                            _derivation.reset(_derivationlevel));

                    // DP_06  IF /driverCategory(<driver>,senior driver) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,200)
                    case 5:
                        return getAdditionalDriverPremium_5(driver,
                            _derivation.reset(_derivationlevel));

                    // DP_08  IF /isHighRiskDriver(<driver>) THEN /additionalDriverPremium(<driver>,1000)
                    case 6:
                        return getAdditionalDriverPremium_6(driver,
                            _derivation.reset(_derivationlevel));

                    // DP_09  IF greater_than(getNumberOfAccidentsInvolvedIn(<driver>),0) THEN /additionalDriverPremium(<driver>,*(150,getNumberOfAccidentsInvolvedIn(<driver>)))
                    case 7:
                        return getAdditionalDriverPremium_7(driver,
                            _derivation.reset(_derivationlevel));

                    // MSD_01  IF isPreferred(<client>) THEN /additionalDriverPremium(<client>,-250)
                    case 8:
                        return getAdditionalDriverPremium_8(driver,
                            _derivation.reset(_derivationlevel));

                    // MSD_02  IF isElite(<client>) THEN /additionalDriverPremium(<client>,-500)
                    case 9:
                        return getAdditionalDriverPremium_9(driver,
                            _derivation.reset(_derivationlevel));

                    default:
                        return EmptyIterator.DEFAULT;
                    } // switch(pos)
                } // getIterator()

                public String getRuleRef(int pos) {
                    switch (pos) {
                    // DP_01  IF /driverCategory(<driver>,young driver) AND isMarried(<driver>) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,700)
                    case 0:
                        return "DP_01";

                    // DP_02  IF /driverCategory(<driver>,young driver) AND isMarried(<driver>) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,720)
                    case 1:
                        return "DP_02";

                    // DP_03  IF /driverCategory(<driver>,young driver) AND isMarried(<driver>) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,300)
                    case 2:
                        return "DP_03";

                    // DP_04  IF /driverCategory(<driver>,young driver) AND isMarried(<driver>) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,300)
                    case 3:
                        return "DP_04";

                    // DP_05  IF /driverCategory(<driver>,senior driver) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,500)
                    case 4:
                        return "DP_05";

                    // DP_06  IF /driverCategory(<driver>,senior driver) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,200)
                    case 5:
                        return "DP_06";

                    // DP_08  IF /isHighRiskDriver(<driver>) THEN /additionalDriverPremium(<driver>,1000)
                    case 6:
                        return "DP_08";

                    // DP_09  IF greater_than(getNumberOfAccidentsInvolvedIn(<driver>),0) THEN /additionalDriverPremium(<driver>,*(150,getNumberOfAccidentsInvolvedIn(<driver>)))
                    case 7:
                        return "DP_09";

                    // MSD_01  IF isPreferred(<client>) THEN /additionalDriverPremium(<client>,-250)
                    case 8:
                        return "MSD_01";

                    // MSD_02  IF isElite(<client>) THEN /additionalDriverPremium(<client>,-500)
                    case 9:
                        return "MSD_02";

                    default:
                        return "";
                    } // switch(pos)
                } // getRuleRef()
            };

        return result;
    }

    /**
     * Method generated for query /additionalDriverPremium[in,out]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of AdditionalDriverPremium
    */
    private static ResourceIterator<AdditionalDriverPremium> getAdditionalDriverPremium_0(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("DP_01", DerivationController.RULE, driver,
            DerivationController.NIL);

        // Variable bindings in rule:  IF /driverCategory(<driver>,young driver) AND isMarried(<driver>) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,700)
        class bindingsInRule50 {
            // Property generated for term  "young driver"
            java.lang.String p1;

            // Property generated for term  "700"
            long p2;

            // Property generated for term  "<driver>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p3;
        }
        ;

        final bindingsInRule50 bindings = new bindingsInRule50();
        bindings.p1 = "young driver";
        bindings.p3 = driver;
        bindings.p2 = 700;

        // code for prereq /driverCategory(<driver>,young driver)
        final ResourceIterator<DriverCategory> iterator1 = KBFragement_getDriverCategory.getDriverCategory(driver,
                "young driver", _derivation.increaseDepth());

        // code for prereq isMarried(<driver>)
        final ResourceIterator<isMarried> iterator2 = new NestedIterator<DriverCategory, isMarried>(iterator1) {
                public ResourceIterator<isMarried> getNextIterator(
                    DriverCategory object) {
                    bindings.p3 = object.driver;
                    bindings.p1 = "young driver";

                    return KBFragement_isMarried_1.isMarried_1(bindings.p3,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq /specialLocation(<driver>)
        final ResourceIterator<specialLocation> iterator3 = new NestedIterator<isMarried, specialLocation>(iterator2) {
                public ResourceIterator<specialLocation> getNextIterator(
                    isMarried object) {
                    bindings.p3 = object.slot1;

                    return KBFragement_specialLocation_1.specialLocation_1(bindings.p3,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq /additionalDriverPremium(<driver>,700)
        final ResourceIterator<AdditionalDriverPremium> iterator4 = new NestedIterator<specialLocation, AdditionalDriverPremium>(iterator3) {
                public ResourceIterator<AdditionalDriverPremium> getNextIterator(
                    specialLocation object) {
                    bindings.p3 = object.slot1;

                    return new SingletonIterator(new AdditionalDriverPremium(
                            bindings.p3, bindings.p2));
                }
            };

        return iterator4;
    }

    /**
     * Method generated for query /additionalDriverPremium[in,out]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of AdditionalDriverPremium
    */
    private static ResourceIterator<AdditionalDriverPremium> getAdditionalDriverPremium_1(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("DP_02", DerivationController.RULE, driver,
            DerivationController.NIL);

        // Variable bindings in rule:  IF /driverCategory(<driver>,young driver) AND isMarried(<driver>) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,720)
        class bindingsInRule51 {
            // Property generated for term  "young driver"
            java.lang.String p1;

            // Property generated for term  "720"
            long p2;

            // Property generated for term  "<driver>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p3;
        }
        ;

        final bindingsInRule51 bindings = new bindingsInRule51();
        bindings.p1 = "young driver";
        bindings.p2 = 720;
        bindings.p3 = driver;

        // code for prereq /driverCategory(<driver>,young driver)
        final ResourceIterator<DriverCategory> iterator1 = KBFragement_getDriverCategory.getDriverCategory(driver,
                "young driver", _derivation.increaseDepth());

        // code for prereq isMarried(<driver>)
        final ResourceIterator<not_isMarried> iterator2 = new NestedIterator<DriverCategory, not_isMarried>(iterator1) {
                public ResourceIterator<not_isMarried> getNextIterator(
                    DriverCategory object) {
                    bindings.p3 = object.driver;
                    bindings.p1 = "young driver";

                    return KBFragement_not_isMarried_1.not_isMarried_1(bindings.p3,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq /specialLocation(<driver>)
        final ResourceIterator<specialLocation> iterator3 = new NestedIterator<not_isMarried, specialLocation>(iterator2) {
                public ResourceIterator<specialLocation> getNextIterator(
                    not_isMarried object) {
                    bindings.p3 = object.slot1;

                    return KBFragement_specialLocation_1.specialLocation_1(bindings.p3,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq /additionalDriverPremium(<driver>,720)
        final ResourceIterator<AdditionalDriverPremium> iterator4 = new NestedIterator<specialLocation, AdditionalDriverPremium>(iterator3) {
                public ResourceIterator<AdditionalDriverPremium> getNextIterator(
                    specialLocation object) {
                    bindings.p3 = object.slot1;

                    return new SingletonIterator(new AdditionalDriverPremium(
                            bindings.p3, bindings.p2));
                }
            };

        return iterator4;
    }

    /**
     * Method generated for query /additionalDriverPremium[in,out]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of AdditionalDriverPremium
    */
    private static ResourceIterator<AdditionalDriverPremium> getAdditionalDriverPremium_2(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("DP_03", DerivationController.RULE, driver,
            DerivationController.NIL);

        // Variable bindings in rule:  IF /driverCategory(<driver>,young driver) AND isMarried(<driver>) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,300)
        class bindingsInRule52 {
            // Property generated for term  "young driver"
            java.lang.String p1;

            // Property generated for term  "<driver>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p2;

            // Property generated for term  "300"
            long p3;
        }
        ;

        final bindingsInRule52 bindings = new bindingsInRule52();
        bindings.p1 = "young driver";
        bindings.p2 = driver;
        bindings.p3 = 300;

        // code for prereq /driverCategory(<driver>,young driver)
        final ResourceIterator<DriverCategory> iterator1 = KBFragement_getDriverCategory.getDriverCategory(driver,
                "young driver", _derivation.increaseDepth());

        // code for prereq isMarried(<driver>)
        final ResourceIterator<isMarried> iterator2 = new NestedIterator<DriverCategory, isMarried>(iterator1) {
                public ResourceIterator<isMarried> getNextIterator(
                    DriverCategory object) {
                    bindings.p2 = object.driver;
                    bindings.p1 = "young driver";

                    return KBFragement_isMarried_1.isMarried_1(bindings.p2,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq /specialLocation(<driver>)
        final ResourceIterator<not_specialLocation> iterator3 = new NestedIterator<isMarried, not_specialLocation>(iterator2) {
                public ResourceIterator<not_specialLocation> getNextIterator(
                    isMarried object) {
                    bindings.p2 = object.slot1;

                    return KBFragement_not_specialLocation_1.not_specialLocation_1(bindings.p2,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq /additionalDriverPremium(<driver>,300)
        final ResourceIterator<AdditionalDriverPremium> iterator4 = new NestedIterator<not_specialLocation, AdditionalDriverPremium>(iterator3) {
                public ResourceIterator<AdditionalDriverPremium> getNextIterator(
                    not_specialLocation object) {
                    bindings.p2 = object.slot1;

                    return new SingletonIterator(new AdditionalDriverPremium(
                            bindings.p2, bindings.p3));
                }
            };

        return iterator4;
    }

    /**
     * Method generated for query /additionalDriverPremium[in,out]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of AdditionalDriverPremium
    */
    private static ResourceIterator<AdditionalDriverPremium> getAdditionalDriverPremium_3(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("DP_04", DerivationController.RULE, driver,
            DerivationController.NIL);

        // Variable bindings in rule:  IF /driverCategory(<driver>,young driver) AND isMarried(<driver>) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,300)
        class bindingsInRule53 {
            // Property generated for term  "young driver"
            java.lang.String p1;

            // Property generated for term  "<driver>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p2;

            // Property generated for term  "300"
            long p3;
        }
        ;

        final bindingsInRule53 bindings = new bindingsInRule53();
        bindings.p1 = "young driver";
        bindings.p2 = driver;
        bindings.p3 = 300;

        // code for prereq /driverCategory(<driver>,young driver)
        final ResourceIterator<DriverCategory> iterator1 = KBFragement_getDriverCategory.getDriverCategory(driver,
                "young driver", _derivation.increaseDepth());

        // code for prereq isMarried(<driver>)
        final ResourceIterator<not_isMarried> iterator2 = new NestedIterator<DriverCategory, not_isMarried>(iterator1) {
                public ResourceIterator<not_isMarried> getNextIterator(
                    DriverCategory object) {
                    bindings.p2 = object.driver;
                    bindings.p1 = "young driver";

                    return KBFragement_not_isMarried_1.not_isMarried_1(bindings.p2,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq /specialLocation(<driver>)
        final ResourceIterator<not_specialLocation> iterator3 = new NestedIterator<not_isMarried, not_specialLocation>(iterator2) {
                public ResourceIterator<not_specialLocation> getNextIterator(
                    not_isMarried object) {
                    bindings.p2 = object.slot1;

                    return KBFragement_not_specialLocation_1.not_specialLocation_1(bindings.p2,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq /additionalDriverPremium(<driver>,300)
        final ResourceIterator<AdditionalDriverPremium> iterator4 = new NestedIterator<not_specialLocation, AdditionalDriverPremium>(iterator3) {
                public ResourceIterator<AdditionalDriverPremium> getNextIterator(
                    not_specialLocation object) {
                    bindings.p2 = object.slot1;

                    return new SingletonIterator(new AdditionalDriverPremium(
                            bindings.p2, bindings.p3));
                }
            };

        return iterator4;
    }

    /**
     * Method generated for query /additionalDriverPremium[in,out]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of AdditionalDriverPremium
    */
    private static ResourceIterator<AdditionalDriverPremium> getAdditionalDriverPremium_4(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("DP_05", DerivationController.RULE, driver,
            DerivationController.NIL);

        // Variable bindings in rule:  IF /driverCategory(<driver>,senior driver) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,500)
        class bindingsInRule54 {
            // Property generated for term  "<driver>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p1;

            // Property generated for term  "500"
            long p2;

            // Property generated for term  "senior driver"
            java.lang.String p3;
        }
        ;

        final bindingsInRule54 bindings = new bindingsInRule54();
        bindings.p1 = driver;
        bindings.p3 = "senior driver";
        bindings.p2 = 500;

        // code for prereq /driverCategory(<driver>,senior driver)
        final ResourceIterator<DriverCategory> iterator1 = KBFragement_getDriverCategory.getDriverCategory(driver,
                "senior driver", _derivation.increaseDepth());

        // code for prereq /specialLocation(<driver>)
        final ResourceIterator<specialLocation> iterator2 = new NestedIterator<DriverCategory, specialLocation>(iterator1) {
                public ResourceIterator<specialLocation> getNextIterator(
                    DriverCategory object) {
                    bindings.p1 = object.driver;
                    bindings.p3 = "senior driver";

                    return KBFragement_specialLocation_1.specialLocation_1(bindings.p1,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq /additionalDriverPremium(<driver>,500)
        final ResourceIterator<AdditionalDriverPremium> iterator3 = new NestedIterator<specialLocation, AdditionalDriverPremium>(iterator2) {
                public ResourceIterator<AdditionalDriverPremium> getNextIterator(
                    specialLocation object) {
                    bindings.p1 = object.slot1;

                    return new SingletonIterator(new AdditionalDriverPremium(
                            bindings.p1, bindings.p2));
                }
            };

        return iterator3;
    }

    /**
     * Method generated for query /additionalDriverPremium[in,out]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of AdditionalDriverPremium
    */
    private static ResourceIterator<AdditionalDriverPremium> getAdditionalDriverPremium_5(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("DP_06", DerivationController.RULE, driver,
            DerivationController.NIL);

        // Variable bindings in rule:  IF /driverCategory(<driver>,senior driver) AND /specialLocation(<driver>) THEN /additionalDriverPremium(<driver>,200)
        class bindingsInRule55 {
            // Property generated for term  "200"
            long p1;

            // Property generated for term  "<driver>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p2;

            // Property generated for term  "senior driver"
            java.lang.String p3;
        }
        ;

        final bindingsInRule55 bindings = new bindingsInRule55();
        bindings.p1 = 200;
        bindings.p2 = driver;
        bindings.p3 = "senior driver";

        // code for prereq /driverCategory(<driver>,senior driver)
        final ResourceIterator<DriverCategory> iterator1 = KBFragement_getDriverCategory.getDriverCategory(driver,
                "senior driver", _derivation.increaseDepth());

        // code for prereq /specialLocation(<driver>)
        final ResourceIterator<not_specialLocation> iterator2 = new NestedIterator<DriverCategory, not_specialLocation>(iterator1) {
                public ResourceIterator<not_specialLocation> getNextIterator(
                    DriverCategory object) {
                    bindings.p2 = object.driver;
                    bindings.p3 = "senior driver";

                    return KBFragement_not_specialLocation_1.not_specialLocation_1(bindings.p2,
                        _derivation.increaseDepth());
                }
            };

        // code for prereq /additionalDriverPremium(<driver>,200)
        final ResourceIterator<AdditionalDriverPremium> iterator3 = new NestedIterator<not_specialLocation, AdditionalDriverPremium>(iterator2) {
                public ResourceIterator<AdditionalDriverPremium> getNextIterator(
                    not_specialLocation object) {
                    bindings.p2 = object.slot1;

                    return new SingletonIterator(new AdditionalDriverPremium(
                            bindings.p2, bindings.p1));
                }
            };

        return iterator3;
    }

    /**
     * Method generated for query /additionalDriverPremium[in,out]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of AdditionalDriverPremium
    */
    private static ResourceIterator<AdditionalDriverPremium> getAdditionalDriverPremium_6(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("DP_08", DerivationController.RULE, driver,
            DerivationController.NIL);

        // Variable bindings in rule:  IF /isHighRiskDriver(<driver>) THEN /additionalDriverPremium(<driver>,1000)
        class bindingsInRule56 {
            // Property generated for term  "1000"
            long p1;

            // Property generated for term  "<driver>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p2;
        }
        ;

        final bindingsInRule56 bindings = new bindingsInRule56();
        bindings.p1 = 1000;
        bindings.p2 = driver;

        // code for prereq /isHighRiskDriver(<driver>)
        final ResourceIterator<HighRiskDriver> iterator1 = KBFragement_isHighRiskDriver.isHighRiskDriver(driver,
                _derivation.increaseDepth());

        // code for prereq /additionalDriverPremium(<driver>,1000)
        final ResourceIterator<AdditionalDriverPremium> iterator2 = new NestedIterator<HighRiskDriver, AdditionalDriverPremium>(iterator1) {
                public ResourceIterator<AdditionalDriverPremium> getNextIterator(
                    HighRiskDriver object) {
                    bindings.p2 = object.driver;

                    return new SingletonIterator(new AdditionalDriverPremium(
                            bindings.p2, bindings.p1));
                }
            };

        return iterator2;
    }

    /**
     * Method generated for query /additionalDriverPremium[in,out]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of AdditionalDriverPremium
    */
    private static ResourceIterator<AdditionalDriverPremium> getAdditionalDriverPremium_7(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("DP_09", DerivationController.RULE, driver,
            DerivationController.NIL);

        // Variable bindings in rule:  IF greater_than(getNumberOfAccidentsInvolvedIn(<driver>),0) THEN /additionalDriverPremium(<driver>,*(150,getNumberOfAccidentsInvolvedIn(<driver>)))
        class bindingsInRule57 {
            // Property generated for term  "150"
            long p1;

            // Property generated for term  "getNumberOfAccidentsInvolvedIn(<driver>)"
            long p2;

            // Property generated for term  "0"
            long p3;

            // Property generated for term  "<driver>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p4;

            // Property generated for term  "*(150,getNumberOfAccidentsInvolvedIn(<driver>))"
            long p5;
        }
        ;

        final bindingsInRule57 bindings = new bindingsInRule57();
        bindings.p2 = driver.getNumberOfAccidentsInvolvedIn();
        bindings.p1 = 150;
        bindings.p3 = 0;
        bindings.p4 = driver;
        bindings.p5 = 150 * bindings.p2;

        // code for prereq greater_than(getNumberOfAccidentsInvolvedIn(<driver>),0)
        final ResourceIterator<greater_than> iterator1 = KBFragement_greater_than_11.greater_than_11(bindings.p2,
                0, _derivation.increaseDepth());

        // code for prereq /additionalDriverPremium(<driver>,*(150,getNumberOfAccidentsInvolvedIn(<driver>)))
        final ResourceIterator<AdditionalDriverPremium> iterator2 = new NestedIterator<greater_than, AdditionalDriverPremium>(iterator1) {
                public ResourceIterator<AdditionalDriverPremium> getNextIterator(
                    greater_than object) {
                    bindings.p2 = (long) object.slot1;
                    bindings.p3 = (long) 0;

                    return new SingletonIterator(new AdditionalDriverPremium(
                            bindings.p4, bindings.p5));
                }
            };

        return iterator2;
    }

    /**
     * Method generated for query /additionalDriverPremium[in,out]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of AdditionalDriverPremium
    */
    private static ResourceIterator<AdditionalDriverPremium> getAdditionalDriverPremium_8(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("MSD_01", DerivationController.RULE, driver,
            DerivationController.NIL);

        // Variable bindings in rule:  IF isPreferred(<client>) THEN /additionalDriverPremium(<client>,-250)
        class bindingsInRule58 {
            // Property generated for term  "-250"
            long p1;

            // Property generated for term  "<client>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p2;
        }
        ;

        final bindingsInRule58 bindings = new bindingsInRule58();
        bindings.p1 = -250;
        bindings.p2 = driver;

        // code for prereq isPreferred(<client>)
        final ResourceIterator<isPreferred> iterator1 = KBFragement_isPreferred_1.isPreferred_1(driver,
                _derivation.increaseDepth());

        // code for prereq /additionalDriverPremium(<client>,-250)
        final ResourceIterator<AdditionalDriverPremium> iterator2 = new NestedIterator<isPreferred, AdditionalDriverPremium>(iterator1) {
                public ResourceIterator<AdditionalDriverPremium> getNextIterator(
                    isPreferred object) {
                    bindings.p2 = object.slot1;

                    return new SingletonIterator(new AdditionalDriverPremium(
                            bindings.p2, bindings.p1));
                }
            };

        return iterator2;
    }

    /**
     * Method generated for query /additionalDriverPremium[in,out]
     * @param driver input parameter generated from slot 0
     * @return an iterator for instances of AdditionalDriverPremium
    */
    private static ResourceIterator<AdditionalDriverPremium> getAdditionalDriverPremium_9(
        final example.nz.org.take.compiler.userv.domainmodel.Driver driver,
        final DerivationController _derivation) {
        _derivation.log("MSD_02", DerivationController.RULE, driver,
            DerivationController.NIL);

        // Variable bindings in rule:  IF isElite(<client>) THEN /additionalDriverPremium(<client>,-500)
        class bindingsInRule59 {
            // Property generated for term  "-500"
            long p1;

            // Property generated for term  "<client>"
            example.nz.org.take.compiler.userv.domainmodel.Driver p2;
        }
        ;

        final bindingsInRule59 bindings = new bindingsInRule59();
        bindings.p1 = -500;
        bindings.p2 = driver;

        // code for prereq isElite(<client>)
        final ResourceIterator<isElite> iterator1 = KBFragement_isElite_1.isElite_1(driver,
                _derivation.increaseDepth());

        // code for prereq /additionalDriverPremium(<client>,-500)
        final ResourceIterator<AdditionalDriverPremium> iterator2 = new NestedIterator<isElite, AdditionalDriverPremium>(iterator1) {
                public ResourceIterator<AdditionalDriverPremium> getNextIterator(
                    isElite object) {
                    bindings.p2 = object.slot1;

                    return new SingletonIterator(new AdditionalDriverPremium(
                            bindings.p2, bindings.p1));
                }
            };

        return iterator2;
    }
}
