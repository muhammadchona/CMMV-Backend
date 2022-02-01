package mz.org.fgh.cmmv.backend.userLogin

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DistrictUserLoginServiceSpec extends Specification {

    DistrictUserLoginService districtUserLoginService
    @Autowired Datastore datastore

    private Long setupData() {
        //new DistrictUserLogin(...).save(flush: true, failOnError: true)
        //new DistrictUserLogin(...).save(flush: true, failOnError: true)
        //DistrictUserLogin districtUserLogin = new DistrictUserLogin(...).save(flush: true, failOnError: true)
        //new DistrictUserLogin(...).save(flush: true, failOnError: true)
        //new DistrictUserLogin(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //districtUserLogin.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        districtUserLoginService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<DistrictUserLogin> districtUserLoginList = districtUserLoginService.list(max: 2, offset: 2)

        then:
        districtUserLoginList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        districtUserLoginService.count() == 5
    }

    void "test delete"() {
        Long districtUserLoginId = setupData()

        expect:
        districtUserLoginService.count() == 5

        when:
        districtUserLoginService.delete(districtUserLoginId)
        datastore.currentSession.flush()

        then:
        districtUserLoginService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        DistrictUserLogin districtUserLogin = new DistrictUserLogin()
        districtUserLoginService.save(districtUserLogin)

        then:
        districtUserLogin.id != null
    }
}
