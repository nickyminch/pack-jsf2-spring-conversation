$output.java($Configuration, "DispatcherServletConfiguration")##

$output.require("java.util.EnumSet")##
$output.require("java.util.Set")##
$output.require("java.util.TreeSet")##

$output.require("javax.servlet.DispatcherType")##
$output.require("javax.servlet.ServletContext")##
$output.require("javax.servlet.SessionTrackingMode")##

$output.require("org.slf4j.Logger")##
$output.require("org.slf4j.LoggerFactory")##
$output.require("org.springframework.context.annotation.Bean")##
$output.require("org.springframework.context.annotation.ComponentScan")##
$output.require("org.springframework.context.annotation.Configuration")##
$output.require("org.springframework.web.filter.DelegatingFilterProxy")##
$output.require("org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer")##

$output.require("com.jaxio.products.util.ResourcesUtil")##
$output.require("com.jaxio.products.web.conversation.ConversationFilter")##
$output.require("com.jaxio.products.web.filter.LocaleResolverRequestFilter")##

$output.require("ch.qos.logback.classic.selector.servlet.LoggerContextFilter")##

@Configuration
@ComponentScan(basePackages = {  "com.jaxio.jpa.querybyexample", "com.jaxio.products"})
public class $output.currentClass extends AbstractAnnotationConfigDispatcherServletInitializer {
	
    private static final Logger log = LoggerFactory.getLogger(DispatcherServletConfiguration.class);

    @Override
    public void onStartup(ServletContext sc){
		sc.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
        sc.addFilter("localeResolverRequestFilter", LocaleResolverRequestFilter.class);
        sc.addFilter("gzipResponseFilter", org.omnifaces.filter.GzipResponseFilter.class);
        sc.addFilter("javamelodyFilter", net.bull.javamelody.MonitoringFilter.class);
        sc.addFilter("logContextFilter", LoggerContextFilter.class);
        sc.addFilter("fileUploadFilter", org.primefaces.webapp.filter.FileUploadFilter.class);
        sc.addFilter("conversationFilter", ConversationFilter.class);

        sc.getFilterRegistration("springSecurityFilterChain")
        	.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC), false, "facesServlet");
        sc.getFilterRegistration("localeResolverRequestFilter")
                .addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC), false, "facesServlet");
        sc.getFilterRegistration("gzipResponseFilter")
                .addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC), false, "facesServlet");
        sc.getFilterRegistration("logContextFilter").addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC),
                false, "facesServlet");
        sc.getFilterRegistration("fileUploadFilter").addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC),
                false, "facesServlet");
        sc.getFilterRegistration("conversationFilter").addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC),
                false, "facesServlet");

        
        sc.addListener(com.sun.faces.config.ConfigureListener.class);

        sc.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
//        try {
//            String facesConfigXML = new File("WEB-INF/faces-config.xml").toURI().toURL().toString();
//        	String facesConfigXML = new URL("WEB-INF/faces-config.xml").toString();
//			sc.setInitParameter("javax.faces.CONFIG_FILES", facesConfigXML);
//		} catch (MalformedURLException e) {
//			log.error(e.getMessage(), e);
//		}
		sc.setInitParameter("javax.faces.CONFIG_FILES", "WEB-INF/faces-config.xml");
		
        sc.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
        sc.setInitParameter("facelets.DEVELOPMENT", Boolean.TRUE.toString());
        sc.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
//        sc.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/taglib/springsecurity.taglib.xml;/WEB-INF/taglib/components.xml");
        //        sc.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "1");
        sc.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/taglib/components.xml");
        sc.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());
        sc.setInitParameter("javax.faces.PARTIAL_STATE_SAVING_METHOD", Boolean.TRUE.toString());
        sc.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
        sc.setInitParameter("javax.faces.STATE_SAVING_METHOD", "server");
        sc.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
        sc.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
        sc.setInitParameter("primefaces.THEME", "bootstrap");
        System.setProperty("org.apache.el.parser.SKIP_IDENTIFIER_CHECK", "true");

        Set<SessionTrackingMode> trackingModes = new TreeSet();
        trackingModes.add(SessionTrackingMode.COOKIE);
        sc.setSessionTrackingModes(trackingModes);

    }

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[] {  RootContextConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] {"/"};
	}
	
	@Bean
	ResourcesUtil resourcesUtil() {
		return ResourcesUtil.getInstance();
	}
}
