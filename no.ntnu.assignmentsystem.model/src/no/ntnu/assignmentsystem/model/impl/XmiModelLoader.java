package no.ntnu.assignmentsystem.model.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import no.ntnu.assignmentsystem.model.ModelFactory;
import no.ntnu.assignmentsystem.model.ModelLoader;
import no.ntnu.assignmentsystem.model.ModelPackage;
import no.ntnu.assignmentsystem.model.UoD;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class XmiModelLoader implements ModelLoader {
	private final File dataFile;
	private final ModelPackage modelPackage;
	
	private Resource resource;
	
	public XmiModelLoader(File dataFile) {
		this.dataFile = dataFile;
		
		// Initialize model package
		modelPackage = ModelPackage.eINSTANCE;
		modelPackage.eClass();
	}
	
	
	// --- ModelLoader ---
	
	@Override
	public UoD getUoD() {
		return (UoD)getResource().getContents().get(0);
	}
	
	@Override
	public EObject findObject(String id) {
		return getResource().getEObject(id);
	}
	
	@Override
	public ModelFactory getFactory() {
		return (ModelFactory)modelPackage.getEFactoryInstance();
	}
	
	@Override
	public void save() {
		Resource resource = getResource();
		
	    try {
	    	resource.save(Collections.EMPTY_MAP);
	    }
	    catch (IOException e1) {
	    	e1.printStackTrace();
	    }
	}
	
	
	// --- Private methods ---
	
	private Resource getResource() {
		if (resource == null) {
			ResourceSet resourceSet = new ResourceSetImpl();
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
			
			URI fileURI = URI.createFileURI(dataFile.getAbsolutePath());
			resource = resourceSet.createResource(fileURI);
			
			try {
				resource.load(Collections.EMPTY_MAP);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		return resource;
	}
}
