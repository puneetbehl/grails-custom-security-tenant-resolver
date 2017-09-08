package demo

import grails.gorm.multitenancy.Tenants
import grails.plugins.rest.client.RestBuilder
import grails.testing.mixin.integration.Integration
import spock.lang.Specification

@Integration
class PlanControllerSpec extends Specification {
    PlanService planService
    UserService userService
    RoleService roleService

    RestBuilder rest = new RestBuilder()

    String accessToken(String u, String p) {
        def resp = rest.post("http://localhost:${serverPort}/api/login") {
            accept('application/json')
            contentType('application/json')
            json {
                username = u
                password = p
            }
        }
        if ( resp.status == 200 ) {
            return resp.json.access_token
        }
        null
    }

    def "Plans for current logged user are retrieved"() {
        given:
        User vector = userService.saveVillain('vector', 'secret')
        User gru = userService.saveVillain('gru', 'secret')
        Tenants.withId("gru") {
            planService.save('Steal the Moon')
        }
        Tenants.withId("vector") {
            planService.save('Steal the Pyramid')
        }

        when: 'login with the gru'
        String gruAccessToken = accessToken('gru', 'secret')

        then:
        gruAccessToken

        when:
        def resp = rest.get("http://localhost:${serverPort}/plan") {
            accept('application/json')
            header('Authorization', "Bearer ${gruAccessToken}")
        }

        then:
        resp.status == 200
        resp.json.toString() == '[{"title":"Steal the Moon"}]'

        when: 'login with the vector'
        String vectorAccessToken = accessToken('vector', 'secret')

        then:
        vectorAccessToken

        when:
        resp = rest.get("http://localhost:${serverPort}/plan") {
            accept('application/json')
            header('Authorization', "Bearer ${vectorAccessToken}")
        }

        then:
        resp.status == 200
        resp.json.toString() == '[{"title":"Steal the Pyramid"}]'

        cleanup:
        userService.deleteUser(gru)
        userService.deleteUser(vector)
        roleService.delete(UserService.ROLE_VILLAIN)
    }
}
