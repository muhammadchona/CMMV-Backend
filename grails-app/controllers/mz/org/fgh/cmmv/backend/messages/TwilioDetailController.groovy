package mz.org.fgh.cmmv.backend.messages

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class TwilioDetailController {

    TwilioDetailService twilioDetailService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond twilioDetailService.list(params), model:[twilioDetailCount: twilioDetailService.count()]
    }

    def show(Long id) {
        respond twilioDetailService.get(id)
    }

    @Transactional
    def save(TwilioDetail twilioDetail) {
        if (twilioDetail == null) {
            render status: NOT_FOUND
            return
        }
        if (twilioDetail.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond twilioDetail.errors
            return
        }

        try {
            twilioDetailService.save(twilioDetail)
        } catch (ValidationException e) {
            respond twilioDetail.errors
            return
        }

        respond twilioDetail, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TwilioDetail twilioDetail) {
        if (twilioDetail == null) {
            render status: NOT_FOUND
            return
        }
        if (twilioDetail.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond twilioDetail.errors
            return
        }

        try {
            twilioDetailService.save(twilioDetail)
        } catch (ValidationException e) {
            respond twilioDetail.errors
            return
        }

        respond twilioDetail, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || twilioDetailService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
