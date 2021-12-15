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
class MobilizerLoginController extends RestfulController{

    MobilizerLoginService mobilizerLoginService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    MobilizerLoginController() {
        super(MobilizerLogin)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond mobilizerLoginService.list(params), model:[mobilizerLoginCount: mobilizerLoginService.count()]
    }

    def show(Long id) {
        respond mobilizerLoginService.get(id)
    }

    @Transactional
    def save(MobilizerLogin mobilizerLogin) {
        if (mobilizerLogin == null) {
            render status: NOT_FOUND
            return
        }
        if (mobilizerLogin.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mobilizerLogin.errors
            return
        }

        try {
            mobilizerLoginService.save(mobilizerLogin)
        } catch (ValidationException e) {
            respond mobilizerLogin.errors
            return
        }

        respond mobilizerLogin, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(MobilizerLogin mobilizerLogin) {
        if (mobilizerLogin == null) {
            render status: NOT_FOUND
            return
        }
        if (mobilizerLogin.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mobilizerLogin.errors
            return
        }

        try {
            mobilizerLoginService.save(mobilizerLogin)
        } catch (ValidationException e) {
            respond mobilizerLogin.errors
            return
        }

        respond mobilizerLogin, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || mobilizerLoginService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
