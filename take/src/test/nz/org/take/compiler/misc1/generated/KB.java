package test.nz.org.take.compiler.misc1.generated;

import nz.org.take.rt.*;


/**
 * Interface generated by the take compiler.
 * @version Tue Sep 18 22:15:33 NZST 2007
 */
@SuppressWarnings("unchecked")
public interface KB {
    /**
     * Method generated for query /is_father_of[in,in]
     * @param son input parameter generated from slot 0
     * @param father input parameter generated from slot 1
     * @return an iterator for instances of IsFatherOf
    */
    public ResultSet<IsFatherOf> getFather(
        final test.nz.org.take.compiler.misc1.Person son,
        final test.nz.org.take.compiler.misc1.Person father);

    /**
    * Method that can be used to query annotations at runtime.
    * @param id the id of the rule (or other knowledge element)
    * @return a map of annotations (string-string mappings)
    * code generated using velocity template AnnotationMethod.vm
    */
    public java.util.Map<String, String> getAnnotations(String id);
}
