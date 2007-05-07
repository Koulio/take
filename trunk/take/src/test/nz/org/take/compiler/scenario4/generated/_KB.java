package test.nz.org.take.compiler.scenario4.generated;

import nz.org.take.rt.*;

import java.util.Iterator;


/**
 * Class generated by the take compiler.
 * @version Mon May 07 15:13:37 NZST 2007
 */
@SuppressWarnings("unchecked")
public class _KB {
    /**
     * Method generated for query nz.org.take.SimplePredicate@db0bece7
     * @param slot1 input parameter generated from slot 0
     * @return an iterator for instances of _isEnrolled
    */
    public ResultSet<_isEnrolled> isEnrolled_10(
        final test.nz.org.take.compiler.scenario4.Student slot1) {
        DerivationController _derivation = new DefaultDerivationController();
        ResultSet<_isEnrolled> _result = new ResultSet(isEnrolled_10(slot1,
                    _derivation), _derivation);

        return _result;
    }

    /**
     * Method generated for query nz.org.take.SimplePredicate@db0bece7
     * @param slot1 input parameter generated from slot 0
     * @return an iterator for instances of _isEnrolled
    */
    private ResourceIterator<_isEnrolled> isEnrolled_10(
        final test.nz.org.take.compiler.scenario4.Student slot1,
        final DerivationController _derivation) {
        final int _derivationlevel = _derivation.getDepth();
        ResourceIterator<_isEnrolled> result = new IteratorChain<_isEnrolled>(1) {
                public Object getIteratorOrObject(int pos) {
                    switch (pos) {
                    // nz.org.take.DerivationRule@c59ad5
                    case 0:
                        return isEnrolled_10_0(slot1,
                            _derivation.reset(_derivationlevel));

                    default:
                        return EmptyIterator.DEFAULT;
                    } // switch(pos)
                } // getIterator()

                public String getRuleRef(int pos) {
                    switch (pos) {
                    // nz.org.take.DerivationRule@c59ad5
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
     * Method generated for query nz.org.take.SimplePredicate@db0bece7
     * @param slot1 input parameter generated from slot 0
     * @return an iterator for instances of _isEnrolled
    */
    private ResourceIterator<_isEnrolled> isEnrolled_10_0(
        final test.nz.org.take.compiler.scenario4.Student slot1,
        final DerivationController _derivation) {
        _derivation.log("rule1"); // Variable bindings in rule: nz.org.take.DerivationRule@c59ad5
        class bindingsInRule1 {
            // Property generated for term  "nz.org.take.Variable@13829d5"
            test.nz.org.take.compiler.scenario4.Student p1;

            // Property generated for term  "nz.org.take.Variable@429c19"
            test.nz.org.take.compiler.scenario4.Course p2;

            // Property generated for term  "nz.org.take.Variable@116318b"
            test.nz.org.take.compiler.scenario4.College p3;
        }
        ;

        final bindingsInRule1 bindings = new bindingsInRule1();
        bindings.p1 = slot1;

        // code for prereq nz.org.take.Prerequisite@ec436
        final ResourceIterator<_courses> iterator1 = courses_10(slot1,
                _derivation.increaseDepth());

        // code for prereq nz.org.take.Prerequisite@173eca6
        final ResourceIterator<_college> iterator2 = new NestedIterator<_courses, _college>(iterator1) {
                public ResourceIterator<_college> getNextIterator(
                    _courses object) {
                    bindings.p1 = object.slot1;
                    bindings.p2 = object.slot2;

                    return college_10(bindings.p2, _derivation.increaseDepth());
                }
            };

        // code for prereq nz.org.take.Fact@1b5a5cf
        final ResourceIterator<_isEnrolled> iterator3 = new NestedIterator<_college, _isEnrolled>(iterator2) {
                public ResourceIterator<_isEnrolled> getNextIterator(
                    _college object) {
                    bindings.p2 = object.slot1;
                    bindings.p3 = object.slot2;

                    return new SingletonIterator(new _isEnrolled(bindings.p1,
                            bindings.p3));
                }
            };

        return iterator3;
    }

    /**
     * Method generated for query nz.org.take.PropertyPredicate@98bbf6
     * @param slot1 input parameter generated from slot 0
     * @return an iterator for instances of _courses
    */
    public ResultSet<_courses> courses_10(
        final test.nz.org.take.compiler.scenario4.Student slot1) {
        DerivationController _derivation = new DefaultDerivationController();
        ResultSet<_courses> _result = new ResultSet(courses_10(slot1,
                    _derivation), _derivation);

        return _result;
    }

    /**
     * Method generated for query nz.org.take.compiler.reference.QueryRef@98bbf6
     * @param source
     * @return an iterator
     * code generated using velocity template nz/org/take/compiler/reference/PropertyPredicateONE2MANY_10_codetemplate.vm
    */
    private ResourceIterator<_courses> courses_10(
        final test.nz.org.take.compiler.scenario4.Student source,
        final DerivationController _derivation) {
        final Iterator<test.nz.org.take.compiler.scenario4.Course> iterator = source.getCourses()
                                                                                    .iterator();
        _derivation.log("property courses");

        ResourceIterator<_courses> result = new ResourceIterator<_courses>() {
                public boolean hasNext() {
                    return iterator.hasNext();
                }

                public void close() {
                    // nothing to do here
                }

                public _courses next() {
                    _courses next = new _courses();
                    next.slot1 = source;
                    next.slot2 = iterator.next();

                    return next;
                }

                public void remove() {
                    throw new UnsupportedOperationException(
                        "Remove is not supported here");
                }
            };

        return result;
    }

    /**
    * Method generated for query nz.org.take.PropertyPredicate@147e668
    * @param slot1 input parameter generated from slot 0
    * @return an iterator for instances of _college
    */
    public ResultSet<_college> college_10(
        final test.nz.org.take.compiler.scenario4.Course slot1) {
        DerivationController _derivation = new DefaultDerivationController();
        ResultSet<_college> _result = new ResultSet(college_10(slot1,
                    _derivation), _derivation);

        return _result;
    }

    /**
     * Method generated for query nz.org.take.compiler.reference.QueryRef@147e668
     * @param source
     * @return an iterator
     * code generated using velocity template nz/org/take/compiler/reference/PropertyPredicateONE2ONE_10_codetemplate.vm
    */
    private ResourceIterator<_college> college_10(
        final test.nz.org.take.compiler.scenario4.Course source,
        final DerivationController _derivation) {
        _derivation.log("property college");

        _college result = new _college();
        result.slot1 = source;
        result.slot2 = source.getCollege();

        return new SingletonIterator<_college>(result);
    }
}
