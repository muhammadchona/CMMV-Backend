package mz.org.fgh.cmmv.backend.userLogin

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class UserLoginServiceSpec extends Specification {

    UserLoginService userLoginService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new UserLogin(...).save(flush: true, failOnError: true)
        //new UserLogin(...).save(flush: true, failOnError: true)
        //UserLogin userLogin = new UserLogin(...).save(flush: true, failOnError: true)
        //new UserLogin(...).save(flush: true, failOnError: true)
        //new UserLogin(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //userLogin.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        userLoginService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<UserLogin> userLoginList = userLoginService.list(max: 2, offset: 2)

        then:
        userLoginList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        userLoginService.count() == 5
    }

    void "test delete"() {
        Long userLoginId = setupData()

        expect:
        userLoginService.count() == 5

        when:
        userLoginService.delete(userLoginId)
        datastore.currentSession.flush()

        then:
        userLoginService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        UserLogin userLogin = new UserLogin()
        userLoginService.save(userLogin)

        then:
        userLogin.id != null
    }
}
