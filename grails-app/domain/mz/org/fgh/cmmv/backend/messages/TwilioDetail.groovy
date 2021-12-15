package mz.org.fgh.cmmv.backend.messages

class TwilioDetail {

    String accountSid
    String authToken

    TwilioDetail (String accountSid,String authToken){
        this.accountSid = accountSid
        this.authToken = authToken
    }


    static constraints = {

        accountSid(nullable: false, blank: false)
        authToken(nullable: false, blank: false)
    }
}
