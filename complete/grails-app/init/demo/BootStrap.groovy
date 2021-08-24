package demo

import grails.gorm.multitenancy.Tenants
import groovy.transform.CompileStatic

@CompileStatic
class BootStrap {
    UserService userService
    PlanDataService planDataService

    def init = { servletContext ->
        User vector = userService.save('vector', 'secret', 'ROLE_VILLAIN')
        Tenants.withId("vector") {
            planDataService.save('Steal a Pyramid')
        }
    }

    def destroy = {
    }
}
