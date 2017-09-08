package demo

import grails.gorm.multitenancy.CurrentTenant
import grails.gorm.services.Service
import groovy.transform.CompileStatic

@CompileStatic
@Service(Plan)
@CurrentTenant
interface PlanService {
    List<Plan> findAll()
    Plan save(String title)
}
