package mz.org.fgh.cmmv.backend.docsOrImages

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.cmmv.backend.utilities.JSONSerializer
import org.apache.http.client.entity.GzipCompressingEntity

import java.nio.charset.Charset
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

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

        List<InfoDocsOrImages> docs = InfoDocsOrImages.list(params)?.collect{
            [id:it.id,title: it.title , forMobilizer: it.forMobilizer]}
      render docs as JSON
    }

    def show(Long id) {
        render JSONSerializer.setJsonObjectResponse(infoDocsOrImagesService.get(id)) as JSON
    }

    @Transactional
    def save(InfoDocsOrImages infoDocsOrImages) {

        infoDocsOrImages.setCreatedDate(new Date())
        infoDocsOrImages.setPublishedDate(new Date())
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
