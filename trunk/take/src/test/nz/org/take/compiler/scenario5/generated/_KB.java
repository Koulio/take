package test.nz.org.take.compiler.scenario5.generated;

import nz.org.take.rt.*;


/**
 * Class generated by the take compiler.
 * @version Fri May 18 15:35:41 NZST 2007
 */
@SuppressWarnings("unchecked")
public class _KB {
    /**
     * Method generated for query /discount[in,out]
     * @param customer input parameter generated from slot 0
     * @return an iterator for instances of discount
    */
    public ResultSet<discount> getDiscount(
        final test.nz.org.take.compiler.scenario5.Customer customer) {
        DerivationController _derivation = new DefaultDerivationController();
        ResultSet<discount> _result = new ResultSet(getDiscount(customer,
                    _derivation), _derivation);

        return _result;
    }

    /**
     * Method generated for query /discount[in,out]
     * @param customer input parameter generated from slot 0
     * @return an iterator for instances of discount
    */
    private ResourceIterator<discount> getDiscount(
        final test.nz.org.take.compiler.scenario5.Customer customer,
        final DerivationController _derivation) {
        final int _derivationlevel = _derivation.getDepth();
        ResourceIterator<discount> result = new IteratorChain<discount>(1) {
                public Object getIteratorOrObject(int pos) {
                    switch (pos) {
                    //  IF equals(getCategory(<customer>),[goldCustomer]) THEN /discount(<customer>,[goldCustomerDiscount])
                    case 0:
                        return getDiscount_0(customer,
                            _derivation.reset(_derivationlevel));

                    default:
                        return EmptyIterator.DEFAULT;
                    } // switch(pos)
                } // getIterator()

                public String getRuleRef(int pos) {
                    switch (pos) {
                    //  IF equals(getCategory(<customer>),[goldCustomer]) THEN /discount(<customer>,[goldCustomerDiscount])
                    case 0:
                        return "rule1";

                    default:
                        return "";
                    } // switch(pos)
                } // getRuleRef()
            };

        return result;
    } // blabla

    /**
     * Method generated for query /discount[in,out]
     * @param customer input parameter generated from slot 0
     * @return an iterator for instances of discount
    */
    private ResourceIterator<discount> getDiscount_0(
        final test.nz.org.take.compiler.scenario5.Customer customer,
        final DerivationController _derivation) {
        _derivation.log("rule1", DerivationController.RULE);

        // Variable bindings in rule:  IF equals(getCategory(<customer>),[goldCustomer]) THEN /discount(<customer>,[goldCustomerDiscount])
        class bindingsInRule1 {
            // Property generated for term  "getCategory(<customer>)"
            test.nz.org.take.compiler.scenario5.CustomerCategory p1;

            // Property generated for term  "<customer>"
            test.nz.org.take.compiler.scenario5.Customer p2;

            // Property generated for term  "[goldCustomer]"
            test.nz.org.take.compiler.scenario5.CustomerCategory p3;

            // Property generated for term  "[goldCustomerDiscount]"
            test.nz.org.take.compiler.scenario5.Discount p4;
        }
        ;

        final bindingsInRule1 bindings = new bindingsInRule1();
        bindings.p4 = Constants.goldCustomerDiscount;
        bindings.p2 = customer;
        bindings.p1 = customer.getCategory();

        // code for prereq equals(getCategory(<customer>),[goldCustomer])
        final ResourceIterator<_equals> iterator1 = equals_11(customer.getCategory(),
                Constants.goldCustomer, _derivation.increaseDepth());

        // code for prereq /discount(<customer>,[goldCustomerDiscount])
        final ResourceIterator<discount> iterator2 = new NestedIterator<_equals, discount>(iterator1) {
                public ResourceIterator<discount> getNextIterator(
                    _equals object) {
                    bindings.p1 = object.slot1;
                    bindings.p3 = (test.nz.org.take.compiler.scenario5.CustomerCategory) Constants.goldCustomer;

                    return new SingletonIterator(new discount(bindings.p2,
                            bindings.p4));
                }
            };

        return iterator2;
    }

    /**
     * Method generated for query equals[in,in]
     * @param slot1 input parameter generated from slot 0
     * @param slot2 input parameter generated from slot 1
     * @return an iterator for instances of _equals
    */
    public ResultSet<_equals> equals_11(
        final test.nz.org.take.compiler.scenario5.CustomerCategory slot1,
        final java.lang.Object slot2) {
        DerivationController _derivation = new DefaultDerivationController();
        ResultSet<_equals> _result = new ResultSet(equals_11(slot1, slot2,
                    _derivation), _derivation);

        return _result;
    }

    /**
     * Method generated for query equals[in,in]
     * @param source
     * @param target
     * @return an iterator
     * code generated using velocity template nz/org/take/compiler/reference/JPredicate_11.vm
    */
    private ResourceIterator<_equals> equals_11(
        final test.nz.org.take.compiler.scenario5.CustomerCategory slot1,
        final java.lang.Object slot2, final DerivationController _derivation) {
        _derivation.log("public boolean test.nz.org.take.compiler.scenario5.CustomerCategory.equals(java.lang.Object)",
            DerivationController.JAVA_METHOD);

        if (slot1.equals(slot2)) {
            _equals result = new _equals();
            result.slot1 = slot1;
            result.slot2 = slot2;

            return new SingletonIterator<_equals>(result);
        }

        return EmptyIterator.DEFAULT;
    }
}
