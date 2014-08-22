package global;

import no.ntnu.assignmentsystem.model.ModelLoader;
import no.ntnu.assignmentsystem.model.impl.XmiModelLoader;
import no.ntnu.assignmentsystem.services.MainServices;
import no.ntnu.assignmentsystem.services.ModelServices;
import no.ntnu.assignmentsystem.services.Services;
import play.Application;
import play.GlobalSettings;

import java.io.File;

public class Global extends GlobalSettings {
    public static Services services;

    @Override
    public void onStart(Application app) {
        File modelData = new File("../no.ntnu.assignmentsystem.model/model/UoD.xmi");
        ModelLoader modelLoader = new XmiModelLoader(modelData);
        ModelServices modelServices = new ModelServices(modelLoader);
        services = new MainServices(modelServices);
    }

    @Override
    public void onStop(Application app) {
        System.out.println("Application shutdown...");
    }

}