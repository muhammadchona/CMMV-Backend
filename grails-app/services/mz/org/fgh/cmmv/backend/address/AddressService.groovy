package mz.org.fgh.cmmv.backend.address

import grails.gorm.services.Service

@Service(Address)
interface AddressService {

    Address get(Serializable id)

    List<Address> list(Map args)

    Long count()

    Address delete(Serializable id)

    Address save(Address address)

}
