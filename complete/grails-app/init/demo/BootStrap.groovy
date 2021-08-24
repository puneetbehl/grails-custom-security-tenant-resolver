package demo

import grails.gorm.multitenancy.Tenants
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired

@CompileStatic
class BootStrap {
    @Autowired UserService userService
    @Autowired PlanDataService planDataService

    def init = { servletContext ->
        User vector = userService.save('vector', 'secret', 'ROLE_VILLAIN')
        Tenants.withId("vector") {
            planDataService.save('Steal a Pyramid')
        }
    }

    def destroy = {
    }
}
