package cmmv.backend

import grails.gorm.transactions.Transactional

import grails.plugin.springsecurity.SecurityFilterPosition
import grails.plugin.springsecurity.SpringSecurityUtils
import groovy.sql.Sql
import com.twilio.Twilio
import mz.org.fgh.cmmv.backend.messages.TwilioDetail
import mz.org.fgh.cmmv.backend.messages.TwilioDetailService;
import mz.org.fgh.cmmv.backend.protection.SecRole
import mz.org.fgh.cmmv.backend.protection.SecUser
import mz.org.fgh.cmmv.backend.protection.SecUserSecRole

import javax.persistence.EntityNotFoundException

class BootStrap {

    public static final String ACCOUNT_SID = "ACb782cd08cd384f29538e90d6a88d8939";
    public static final String AUTH_TOKEN = "b7a856b44c9af30cc95837943ab0d47c";

    TwilioDetailService twilioDetailService;

    def init = { servletContext ->
        addInitialUsers()
        addTwilioDetails()
        def twilioDetails = twilioDetailService.list().get(0)
        Twilio.init(twilioDetails.getAccountSid(),twilioDetails.getAuthToken());
    }
    def destroy = {
    }

    @Transactional
    void addInitialUsers(){

        SpringSecurityUtils.clientRegisterFilter("corsFilterTest", SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order - 1)

        def adminRole = new SecRole('ROLE_ADMIN').save()
        def userRole = new SecRole('ROLE_USER').save()

        def testUser = new SecUser('user', 'password','user').save()
        def adminUser = new SecUser('admin', 'admin','admin').save()

        SecUserSecRole.create testUser, userRole
        SecUserSecRole.create adminUser, adminRole

        SecUserSecRole.withSession {
            it.flush()
            it.clear()
        }
    }

    @Transactional
    void addTwilioDetails(){

         def twilioDetail = twilioDetailService.list()
         if(twilioDetail.size() <= 0) {
             def twilio = new TwilioDetail("ACb782cd08cd384f29538e90d6a88d8939","b7a856b44c9af30cc95837943ab0d47c");
             twilioDetailService.save(twilio)
         }

    }
}
