package mz.org.fgh.cmmv.backend.mobilizer

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ICommunityMobilizerServiceSpec extends Specification {

    ICommunityMobilizerService communityMobilizerService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new CommunityMobilizer(...).save(flush: true, failOnError: true)
        //new CommunityMobilizer(...).save(flush: true, failOnError: true)
        //CommunityMobilizer communityMobilizer = new CommunityMobilizer(...).save(flush: true, failOnError: true)
        //new CommunityMobilizer(...).save(flush: true, failOnError: true)
        //new CommunityMobilizer(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //communityMobilizer.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        communityMobilizerService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<CommunityMobilizer> communityMobilizerList = communityMobilizerService.list(max: 2, offset: 2)

        then:
        communityMobilizerList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        communityMobilizerService.count() == 5
    }

    void "test delete"() {
        Long communityMobilizerId = setupData()

        expect:
        communityMobilizerService.count() == 5

        when:
        communityMobilizerService.delete(communityMobilizerId)
        datastore.currentSession.flush()

        then:
        communityMobilizerService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        CommunityMobilizer communityMobilizer = new CommunityMobilizer()
        communityMobilizerService.save(communityMobilizer)

        then:
        communityMobilizer.id != null
    }
}
