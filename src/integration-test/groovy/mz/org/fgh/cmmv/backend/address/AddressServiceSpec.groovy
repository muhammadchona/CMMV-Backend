package mz.org.fgh.cmmv.backend.address

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AddressServiceSpec extends Specification {

    AddressService addressService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Address(...).save(flush: true, failOnError: true)
        //new Address(...).save(flush: true, failOnError: true)
        //Address address = new Address(...).save(flush: true, failOnError: true)
        //new Address(...).save(flush: true, failOnError: true)
        //new Address(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //address.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        addressService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Address> addressList = addressService.list(max: 2, offset: 2)

        then:
        addressList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        addressService.count() == 5
    }

    void "test delete"() {
        Long addressId = setupData()

        expect:
        addressService.count() == 5

        when:
        addressService.delete(addressId)
        datastore.currentSession.flush()

        then:
        addressService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Address address = new Address()
        addressService.save(address)

        then:
        address.id != null
    }
}
