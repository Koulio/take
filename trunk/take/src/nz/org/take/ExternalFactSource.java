/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package nz.org.take;

/**
 * Interface for external fact sources.
 * Fact sources are factories for ExternalFactStores (in the runtime package)!. 
 * Implementation classes should also meet the following two conditions:
 * <ol>
 * <li>they should be beans, i.e. instantiable using reflection</li>
 * <li>the predicate should be simple</li>
 * <li>instances should be stateless</li>
 * </ol>
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public interface ExternalFactSource extends KnowledgeElement {
	/**
	 * Get an external fact store.
	 * @return a fact store
	 */
	public nz.org.take.rt.ExternalFactStore getFactStore();
}
