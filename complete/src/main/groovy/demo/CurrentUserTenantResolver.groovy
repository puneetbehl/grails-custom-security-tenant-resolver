package demo

import grails.plugin.springsecurity.SpringSecurityService
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import org.grails.datastore.mapping.multitenancy.TenantResolver
import org.grails.datastore.mapping.multitenancy.exceptions.TenantNotFoundException
import org.springframework.beans.factory.annotation.Autowired

@CompileStatic
class CurrentUserTenantResolver implements TenantResolver {

    @Autowired
    SpringSecurityService springSecurityService


    @Override
    Serializable resolveTenantIdentifier() throws TenantNotFoundException {

        String username = loggedUsername()
        if ( username ) {
            return username
        }
        throw new TenantNotFoundException("Tenant could not be resolved from Spring Security Principal")
    }

    @CompileDynamic
    String loggedUsername() {
        springSecurityService.principal?.username
    }
}
