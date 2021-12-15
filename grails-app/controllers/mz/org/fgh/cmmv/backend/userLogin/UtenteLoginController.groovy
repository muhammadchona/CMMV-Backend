package mz.org.fgh.cmmv.backend.userLogin

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class UtenteLoginController extends RestfulController{

    UtenteLoginService utenteLoginService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    UtenteLoginController() {
        super(UtenteLogin)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond utenteLoginService.list(params), model:[utenteLoginCount: utenteLoginService.count()]
    }

    def show(Long id) {
        respond utenteLoginService.get(id)
    }

    @Transactional
    def save(UtenteLogin utenteLogin) {
        if (utenteLogin == null) {
            render status: NOT_FOUND
            return
        }
        if (utenteLogin.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond utenteLogin.errors
            return
        }

        try {
            utenteLoginService.save(utenteLogin)
        } catch (ValidationException e) {
            respond utenteLogin.errors
            return
        }

        respond utenteLogin, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(UtenteLogin utenteLogin) {
        if (utenteLogin == null) {
            render status: NOT_FOUND
            return
        }
        if (utenteLogin.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond utenteLogin.errors
            return
        }

        try {
            utenteLoginService.save(utenteLogin)
        } catch (ValidationException e) {
            respond utenteLogin.errors
            return
        }

        respond utenteLogin, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || utenteLoginService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
