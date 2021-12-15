package mz.org.fgh.cmmv.backend.docsOrImages

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class InfoDocsOrImagesController extends RestfulController{

    InfoDocsOrImagesService infoDocsOrImagesService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    InfoDocsOrImagesController() {
        super(InfoDocsOrImages)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)

        JSON.use('deep'){
            render infoDocsOrImagesService.list(params) as JSON
        }
    }

    def show(Long id) {
        JSON.use('deep'){
            render infoDocsOrImagesService.get(id) as JSON
        }
    }

    @Transactional
    def save(InfoDocsOrImages infoDocsOrImages) {
        if (infoDocsOrImages == null) {
            render status: NOT_FOUND
            return
        }
        if (infoDocsOrImages.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond infoDocsOrImages.errors
            return
        }

        try {
            infoDocsOrImagesService.save(infoDocsOrImages)
        } catch (ValidationException e) {
            respond infoDocsOrImages.errors
            return
        }

        respond infoDocsOrImages, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(InfoDocsOrImages infoDocsOrImages) {
        if (infoDocsOrImages == null) {
            render status: NOT_FOUND
            return
        }
        if (infoDocsOrImages.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond infoDocsOrImages.errors
            return
        }

        try {
            infoDocsOrImagesService.save(infoDocsOrImages)
        } catch (ValidationException e) {
            respond infoDocsOrImages.errors
            return
        }

        respond infoDocsOrImages, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || infoDocsOrImagesService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
