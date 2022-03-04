$output.java($Configuration, "RootContextConfig")##

$output.require("javax.servlet.ServletContext")##
$output.require("javax.servlet.ServletException")##
$output.require("javax.servlet.ServletRegistration")##

$output.require("org.springframework.context.annotation.ComponentScan")##
$output.require("org.springframework.context.annotation.Configuration")##
$output.require("org.springframework.context.annotation.Import")##
$output.require("org.springframework.web.WebApplicationInitializer")##

$output.require("com.jaxio.products.security.FormLoginSecurityConfig")##

@Configuration
@ComponentScan(basePackages = {"com.jaxio.products"})
@Import({ FormLoginSecurityConfig.class })
public class $output.currentClass implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext sc) throws ServletException {
	      // Manage the lifecycle of the root application context
        ServletRegistration.Dynamic facesServlet = sc.addServlet("facesServlet", javax.faces.webapp.FacesServlet.class);
        facesServlet.setLoadOnStartup(1);
        
        //        facesServlet.setAsyncSupported(true);

        if (facesServlet != null) {
            facesServlet.addMapping("*.faces");
        } 
	}
}
