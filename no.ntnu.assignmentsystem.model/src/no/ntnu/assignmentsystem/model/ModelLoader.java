package no.ntnu.assignmentsystem.model;

import org.eclipse.emf.ecore.EObject;

public interface ModelLoader {
	UoD getUoD();
	EObject findObject(String id);
	ModelFactory getFactory();
	void save();
}
