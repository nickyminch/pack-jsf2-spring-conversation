$output.java($Configuration, "AppContext")##

$output.require("javax.inject.Singleton")##
$output.require("org.springframework.context.ApplicationContext")##

@Singleton
public class $output.currentClass {

    private static AppContext instance;

    public static synchronized AppContext getInstance() {
    	if(instance==null) {
    		new AppContext();
    	}
        return instance;
    }

    private ApplicationContext applicationContext;

    /**
     * Default constructor
     */
    private AppContext() {
    	instance = this;
    }

    /**
     * 
     */
    public void setContext(ApplicationContext applicationContext) {     
        this.applicationContext = applicationContext;
    }

    /**
     * 
     * @return
     */
    public ApplicationContext getContext() {
        return applicationContext;
    }
}