package state;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import resource.URLResource;

public class urlApplication extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        //new urlApplication().run(new String[] {"server"});
    }

    public void run(Configuration configuration, Environment environment) {
        environment.jersey().register(new URLResource());
    }
}
