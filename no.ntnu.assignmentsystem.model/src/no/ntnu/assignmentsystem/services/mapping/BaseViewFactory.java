package no.ntnu.assignmentsystem.services.mapping;

import no.ntnu.assignmentsystem.services.ServicesFactory;

class BaseViewFactory {
	protected static ServicesFactory getFactory() {
		return ServicesFactory.eINSTANCE;
	}
}
