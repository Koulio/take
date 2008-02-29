/*
 * Copyright 2007 Bastian Schenke Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
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
