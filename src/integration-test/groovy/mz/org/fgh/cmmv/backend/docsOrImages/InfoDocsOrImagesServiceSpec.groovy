package mz.org.fgh.cmmv.backend.docsOrImages

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class InfoDocsOrImagesServiceSpec extends Specification {

    InfoDocsOrImagesService infoDocsOrImagesService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new InfoDocsOrImages(...).save(flush: true, failOnError: true)
        //new InfoDocsOrImages(...).save(flush: true, failOnError: true)
        //InfoDocsOrImages infoDocsOrImages = new InfoDocsOrImages(...).save(flush: true, failOnError: true)
        //new InfoDocsOrImages(...).save(flush: true, failOnError: true)
        //new InfoDocsOrImages(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //infoDocsOrImages.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        infoDocsOrImagesService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<InfoDocsOrImages> infoDocsOrImagesList = infoDocsOrImagesService.list(max: 2, offset: 2)

        then:
        infoDocsOrImagesList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        infoDocsOrImagesService.count() == 5
    }

    void "test delete"() {
        Long infoDocsOrImagesId = setupData()

        expect:
        infoDocsOrImagesService.count() == 5

        when:
        infoDocsOrImagesService.delete(infoDocsOrImagesId)
        datastore.currentSession.flush()

        then:
        infoDocsOrImagesService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        InfoDocsOrImages infoDocsOrImages = new InfoDocsOrImages()
        infoDocsOrImagesService.save(infoDocsOrImages)

        then:
        infoDocsOrImages.id != null
    }
}
