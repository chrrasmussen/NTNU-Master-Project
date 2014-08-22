package no.ntnu.assignmentsystem.services.mapping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

class Mapper {
	public static void copyAttributes(EObject fromObject, EObject toObject) {
		fromObject.eClass().getEAllAttributes().stream().forEach(fromAttribute -> {
			EStructuralFeature toAttribute = toObject.eClass().getEStructuralFeature(fromAttribute.getName());
			if (toAttribute != null) {
				Object value = fromObject.eGet(fromAttribute);
				toObject.eSet(toAttribute, value);
			}
	    });
	}
}
