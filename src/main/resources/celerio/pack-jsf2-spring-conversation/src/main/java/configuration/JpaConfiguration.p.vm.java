$output.java($Configuration, "JpaConfiguration")##

$output.require("java.util.Properties")##

$output.require("javax.inject.Inject")##
$output.require("javax.naming.NamingException")##
$output.require("javax.sql.DataSource")##

$output.require("org.springframework.beans.factory.annotation.Value")##
$output.require("org.springframework.context.annotation.Bean")##
$output.require("org.springframework.context.annotation.Configuration")##
$output.require("org.springframework.context.annotation.PropertySource")##
$output.require("org.springframework.core.env.Environment")##
$output.require("org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor")##
$output.require("org.springframework.orm.jpa.JpaTransactionManager")##
$output.require("org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean")##
$output.require("org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter")##
$output.require("org.springframework.web.servlet.config.annotation.EnableWebMvc")##
$output.require("org.springframework.jdbc.datasource.DriverManagerDataSource")##
$output.require("org.springframework.data.jpa.repository.config.EnableJpaRepositories")##
$output.require("org.springframework.context.annotation.PropertySource")##

@Configuration
@EnableJpaRepositories(basePackages = {"com.jaxio.products.repository"})
@PropertySource("classpath:/application.properties")
public class $output.currentClass {

    @Value("classpath:hibernate.properties")
    private Properties jpaProperties;
    
    @Inject
    Environment env;

    /**
     * Enable exception translation for beans annotated with @Repository
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * @see read http://www.springframework.org/docs/reference/transaction.html
     */
    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    /**
     * Build the entity manager with Hibernate as a provider.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        // We set the persistenceXmlLocation to a different name to make it work on JBoss.
        emf.setPersistenceXmlLocation("classpath:META-INF/spring-persistence.xml");
        emf.setPersistenceUnitName("${configuration.applicationName}PU");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaProperties(jpaProperties);
        return emf;
    }
    
    public DataSource dataSource(){
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		
		dataSource.setUsername(env.getProperty("spring.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.datasource.password"));
		dataSource.setCatalog(env.getProperty("spring.datasource.catalog"));
		return dataSource;
    }
}
