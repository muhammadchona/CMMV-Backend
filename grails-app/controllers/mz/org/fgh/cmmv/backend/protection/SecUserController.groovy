package mz.org.fgh.cmmv.backend.protection

import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.cmmv.backend.userLogin.MobilizerLogin

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class SecUserController extends RestfulController {

    SecUserService secUserService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    SecUserController() {
        super(SecUser)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond secUserService.list(params), model:[secUserCount: secUserService.count()]
    }

    def show(Long id) {
        respond secUserService.get(id)
    }

    @Transactional
    def save(SecUser secUser) {

    //   secUser.fullName = secUser.username
        SecRole secRole = SecRole.findByAuthority('ROLE_USER')

        if (secUser == null) {
            render status: NOT_FOUND
            return
        }
        if (secUser.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond secUser.errors
            return
        }

        try {
            secUserService.save(secUser)
            SecUserSecRole.create secUser, secRole
        } catch (ValidationException e) {
            respond secUser.errors
            return
        }

        respond secUser, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(SecUser secUser) {
        if (secUser == null) {
            render status: NOT_FOUND
            return
        }
        if (secUser.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond secUser.errors
            return
        }

        try {
            secUserService.save(secUser)
        } catch (ValidationException e) {
            respond secUser.errors
            return
        }

        respond secUser, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || secUserService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    @Transactional
    def changePassword(SecUser secUser) {

        try {
            SecUser secUserDb =  SecUser.findById(secUser.id)
            secUserDb.password = secUser.password
            secUserService.save(secUserDb)
        } catch (ValidationException e) {
            respond secRole.errors
            return
        }

        respond secUser, [status: OK, view:"show"]
    }
}
