package no.ntnu.assignmentsystem.editor;

import org.osgi.framework.BundleContext;

import akka.actor.ActorSystem;
import akka.osgi.ActorSystemActivator;

public class Activator extends ActorSystemActivator {
	public static ActorSystem actorSystem;
	
	@Override
	public void configure(BundleContext context, ActorSystem system) {
		System.out.println("Configuring Activator");
		registerService(context, system);
		
		actorSystem = system;
	}
}
