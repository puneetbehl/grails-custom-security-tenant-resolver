package demo

import grails.gorm.MultiTenant

class Plan implements MultiTenant<Plan> {
    String title
    String username

    static mapping = {
        tenantId name: 'username'
    }
}
