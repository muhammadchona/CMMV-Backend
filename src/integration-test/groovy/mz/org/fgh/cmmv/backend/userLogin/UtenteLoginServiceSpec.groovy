package mz.org.fgh.cmmv.backend.userLogin

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class UtenteLoginServiceSpec extends Specification {

    UtenteLoginService utenteLoginService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new UtenteLogin(...).save(flush: true, failOnError: true)
        //new UtenteLogin(...).save(flush: true, failOnError: true)
        //UtenteLogin utenteLogin = new UtenteLogin(...).save(flush: true, failOnError: true)
        //new UtenteLogin(...).save(flush: true, failOnError: true)
        //new UtenteLogin(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //utenteLogin.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        utenteLoginService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<UtenteLogin> utenteLoginList = utenteLoginService.list(max: 2, offset: 2)

        then:
        utenteLoginList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        utenteLoginService.count() == 5
    }

    void "test delete"() {
        Long utenteLoginId = setupData()

        expect:
        utenteLoginService.count() == 5

        when:
        utenteLoginService.delete(utenteLoginId)
        datastore.currentSession.flush()

        then:
        utenteLoginService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        UtenteLogin utenteLogin = new UtenteLogin()
        utenteLoginService.save(utenteLogin)

        then:
        utenteLogin.id != null
    }
}
