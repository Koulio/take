package nz.org.take.r2ml;

import java.util.List;

import nz.org.take.Fact;
import nz.org.take.r2ml.reference.AssociationAsReferenceResolvPolicy;
import nz.org.take.r2ml.reference.SimpleAssociationResolvPolicy;

import de.tu_cottbus.r2ml.AssociationAtom;

public interface AssociationResolvPolicy {
	
	
	/**
	 * Associations are interpretted as simple Predicates.
	 */
	public static AssociationResolvPolicy SIMPLE_ASSOCIATION_RESOLV_POLICY = new SimpleAssociationResolvPolicy();
	
	/**
	 * Associations are interpreted as references between objects.
	 */
	public static AssociationAsReferenceResolvPolicy ASSOCIATION_AS_REFERENCE_RESOLV_POLICY = new AssociationAsReferenceResolvPolicy();
	
	public Fact resolv(AssociationAtom atom) throws R2MLException;
	
}
