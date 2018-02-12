package demo

import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

@CompileStatic
class UserService {
    RoleDataService roleDataService

    UserRoleDataService userRoleDataService

    UserDataService userDataService

    @Transactional
    User save(String username, String password, String authority) {
        User user = userDataService.save(username, password)

        Role role = roleDataService.findByAuthority(authority)
        if ( !role ) {
            role = roleDataService.saveByAuthority(authority)
        }

        userRoleDataService.save(user, role)
        user
    }
}