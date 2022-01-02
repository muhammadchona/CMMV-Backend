package mz.org.fgh.cmmv.backend.protection

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class SecRoleController extends RestfulController {

    ISecRoleService secRoleService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    SecRoleController() {
        super(SecRole)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond secRoleService.list(params), model:[secRoleCount: secRoleService.count()]
    }

    def show(Long id) {
        respond secRoleService.get(id)
    }

    @Transactional
    def save(SecRole secRole) {
        if (secRole == null) {
            render status: NOT_FOUND
            return
        }
        if (secRole.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond secRole.errors
            return
        }

        try {
            secRoleService.save(secRole)
        } catch (ValidationException e) {
            respond secRole.errors
            return
        }

        respond secRole, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(SecRole secRole) {
        if (secRole == null) {
            render status: NOT_FOUND
            return
        }
        if (secRole.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond secRole.errors
            return
        }

        try {
            secRoleService.save(secRole)
        } catch (ValidationException e) {
            respond secRole.errors
            return
        }

        respond secRole, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || secRoleService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def getSecRoleByAuthority(String authority) {
        respond secRoleService.getSecRoleByAuthority(authority)
    }
}
