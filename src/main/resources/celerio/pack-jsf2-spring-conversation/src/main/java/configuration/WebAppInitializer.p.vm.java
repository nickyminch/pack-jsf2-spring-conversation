$output.java($Configuration, "WebAppInitializer")##

$output.require("javax.servlet.ServletContext")##
$output.require("javax.servlet.ServletException")##
$output.require("javax.servlet.ServletRegistration")##

$output.require("org.springframework.context.annotation.Configuration")##
$output.require("org.springframework.transaction.annotation.EnableTransactionManagement")##
$output.require("org.springframework.web.WebApplicationInitializer")##
$output.require("org.springframework.web.context.ContextLoaderListener")##
$output.require("org.springframework.web.context.support.AnnotationConfigWebApplicationContext")##
$output.require("org.springframework.web.servlet.DispatcherServlet")##
$output.require("org.springframework.web.servlet.config.annotation.EnableWebMvc")##

@Configuration
@EnableWebMvc
@EnableTransactionManagement
public class $output.currentClass implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		// Create the 'root' Spring application context
		AnnotationConfigWebApplicationContext rootContext =
			new AnnotationConfigWebApplicationContext();
		rootContext.register(RootContextConfig.class);
		
		container.addListener(new ContextLoaderListener(rootContext));

		// Create the dispatcher servlet's Spring application context
		AnnotationConfigWebApplicationContext dispatcherContext =
			new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(DispatcherServletConfiguration.class);

		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic dispatcher =
				container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
}
