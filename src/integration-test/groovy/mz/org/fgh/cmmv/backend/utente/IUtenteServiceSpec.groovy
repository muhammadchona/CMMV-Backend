package mz.org.fgh.cmmv.backend.utente

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class IUtenteServiceSpec extends Specification {

    IUtenteService utenteService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Utente(...).save(flush: true, failOnError: true)
        //new Utente(...).save(flush: true, failOnError: true)
        //Utente utente = new Utente(...).save(flush: true, failOnError: true)
        //new Utente(...).save(flush: true, failOnError: true)
        //new Utente(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //utente.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        utenteService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Utente> utenteList = utenteService.list(max: 2, offset: 2)

        then:
        utenteList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        utenteService.count() == 5
    }

    void "test delete"() {
        Long utenteId = setupData()

        expect:
        utenteService.count() == 5

        when:
        utenteService.delete(utenteId)
        datastore.currentSession.flush()

        then:
        utenteService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Utente utente = new Utente()
        utenteService.save(utente)

        then:
        utente.id != null
    }
}
