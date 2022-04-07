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
class FrontlineSmsDetailsController {

    FrontlineSmsDetailsService frontlineSmsDetailsService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond frontlineSmsDetailsService.list(params), model:[frontlineSmsDetailsCount: frontlineSmsDetailsService.count()]
    }

    def show(Long id) {
        respond frontlineSmsDetailsService.get(id)
    }

    @Transactional
    def save(FrontlineSmsDetails frontlineSmsDetails) {
        if (frontlineSmsDetails == null) {
            render status: NOT_FOUND
            return
        }
        if (frontlineSmsDetails.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond frontlineSmsDetails.errors
            return
        }

        try {
            frontlineSmsDetailsService.save(frontlineSmsDetails)
        } catch (ValidationException e) {
            respond frontlineSmsDetails.errors
            return
        }

        respond frontlineSmsDetails, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(FrontlineSmsDetails frontlineSmsDetails) {
        if (frontlineSmsDetails == null) {
            render status: NOT_FOUND
            return
        }
        if (frontlineSmsDetails.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond frontlineSmsDetails.errors
            return
        }

        try {
            frontlineSmsDetailsService.save(frontlineSmsDetails)
        } catch (ValidationException e) {
            respond frontlineSmsDetails.errors
            return
        }

        respond frontlineSmsDetails, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || frontlineSmsDetailsService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
