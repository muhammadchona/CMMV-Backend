package mz.org.fgh.cmmv.backend.messages

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class TwilioDetailServiceSpec extends Specification {

    TwilioDetailService twilioDetailService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new TwilioDetail(...).save(flush: true, failOnError: true)
        //new TwilioDetail(...).save(flush: true, failOnError: true)
        //TwilioDetail twilioDetail = new TwilioDetail(...).save(flush: true, failOnError: true)
        //new TwilioDetail(...).save(flush: true, failOnError: true)
        //new TwilioDetail(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //twilioDetail.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        twilioDetailService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<TwilioDetail> twilioDetailList = twilioDetailService.list(max: 2, offset: 2)

        then:
        twilioDetailList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        twilioDetailService.count() == 5
    }

    void "test delete"() {
        Long twilioDetailId = setupData()

        expect:
        twilioDetailService.count() == 5

        when:
        twilioDetailService.delete(twilioDetailId)
        datastore.currentSession.flush()

        then:
        twilioDetailService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        TwilioDetail twilioDetail = new TwilioDetail()
        twilioDetailService.save(twilioDetail)

        then:
        twilioDetail.id != null
    }
}
