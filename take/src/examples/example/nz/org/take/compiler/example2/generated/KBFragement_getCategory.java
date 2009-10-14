package example.nz.org.take.compiler.example2.generated;

import nz.org.take.rt.*;


/**
 * Class generated by the take compiler.
 * @version Wed Mar 26 00:18:13 NZST 2008
 */
@SuppressWarnings("unchecked")
class KBFragement_getCategory {
    /**
     * Method generated for query /category[in,out]
     * @param customer input parameter generated from slot 0
     * @return an iterator for instances of CustomerCategory
    */
    public static ResultSet<CustomerCategory> getCategory(
        final example.nz.org.take.compiler.example2.Customer customer) {
        DerivationController _derivation = new DefaultDerivationController();
        ResultSet<CustomerCategory> _result = new ResultSet(KBFragement_getCategory.getCategory(
                    customer, _derivation), _derivation);

        return _result;
    }

    /**
     * Method generated for query /category[in,out]
     * @param customer input parameter generated from slot 0
     * @return an iterator for instances of CustomerCategory
    */
    static ResourceIterator<CustomerCategory> getCategory(
        final example.nz.org.take.compiler.example2.Customer customer,
        final DerivationController _derivation) {
        final int _derivationlevel = _derivation.getDepth();
        ResourceIterator<CustomerCategory> result = new IteratorChain<CustomerCategory>(1) {
                public Object getIteratorOrObject(int pos) {
                    switch (pos) {
                    // rule2  IF greater_than(100,getTurnover(<c>,12)) THEN /category(<c>,gold)
                    case 0:
                        return getCategory_0(customer,
                            _derivation.reset(_derivationlevel));

                    default:
                        return EmptyIterator.DEFAULT;
                    } // switch(pos)
                } // getIterator()

                public String getRuleRef(int pos) {
                    switch (pos) {
                    // rule2  IF greater_than(100,getTurnover(<c>,12)) THEN /category(<c>,gold)
                    case 0:
                        return "rule2";

                    default:
                        return "";
                    } // switch(pos)
                } // getRuleRef()
            };

        return result;
    }

    /**
     * Method generated for query /category[in,out]
     * @param customer input parameter generated from slot 0
     * @return an iterator for instances of CustomerCategory
    */
    private static ResourceIterator<CustomerCategory> getCategory_0(
        final example.nz.org.take.compiler.example2.Customer customer,
        final DerivationController _derivation) {
        _derivation.log("rule2", DerivationController.RULE, customer,
            DerivationController.NIL);

        // Variable bindings in rule:  IF greater_than(100,getTurnover(<c>,12)) THEN /category(<c>,gold)
        class bindingsInRule1 {
            // Property generated for term  "12"
            long p1;

            // Property generated for term  "<c>"
            example.nz.org.take.compiler.example2.Customer p2;

            // Property generated for term  "100"
            long p3;

            // Property generated for term  "gold"
            java.lang.String p4;

            // Property generated for term  "getTurnover(<c>,12)"
            long p5;
        }
        ;

        final bindingsInRule1 bindings = new bindingsInRule1();
        bindings.p2 = customer;
        bindings.p1 = 12;
        bindings.p3 = 100;
        bindings.p4 = "gold";
        bindings.p5 = customer.getTurnover(12);

        // code for prereq greater_than(100,getTurnover(<c>,12))
        final ResourceIterator<greater_than> iterator1 = KBFragement_greater_than_11.greater_than_11(100,
                bindings.p5, _derivation.increaseDepth());

        // code for prereq /category(<c>,gold)
        final ResourceIterator<CustomerCategory> iterator2 = new NestedIterator<greater_than, CustomerCategory>(iterator1) {
                public ResourceIterator<CustomerCategory> getNextIterator(
                    greater_than object) {
                    bindings.p3 = (long) 100;
                    bindings.p5 = (long) object.slot2;

                    return new SingletonIterator(new CustomerCategory(
                            bindings.p2, bindings.p4));
                }
            };

        return iterator2;
    }
}