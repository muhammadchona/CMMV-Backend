package mz.org.fgh.cmmv.backend.userLogin

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class MobilizerLoginServiceSpec extends Specification {

    MobilizerLoginService mobilizerLoginService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new MobilizerLogin(...).save(flush: true, failOnError: true)
        //new MobilizerLogin(...).save(flush: true, failOnError: true)
        //MobilizerLogin mobilizerLogin = new MobilizerLogin(...).save(flush: true, failOnError: true)
        //new MobilizerLogin(...).save(flush: true, failOnError: true)
        //new MobilizerLogin(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //mobilizerLogin.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        mobilizerLoginService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<MobilizerLogin> mobilizerLoginList = mobilizerLoginService.list(max: 2, offset: 2)

        then:
        mobilizerLoginList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        mobilizerLoginService.count() == 5
    }

    void "test delete"() {
        Long mobilizerLoginId = setupData()

        expect:
        mobilizerLoginService.count() == 5

        when:
        mobilizerLoginService.delete(mobilizerLoginId)
        datastore.currentSession.flush()

        then:
        mobilizerLoginService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        MobilizerLogin mobilizerLogin = new MobilizerLogin()
        mobilizerLoginService.save(mobilizerLogin)

        then:
        mobilizerLogin.id != null
    }
}
