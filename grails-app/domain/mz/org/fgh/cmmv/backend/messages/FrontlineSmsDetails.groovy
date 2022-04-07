package mz.org.fgh.cmmv.backend.messages

class FrontlineSmsDetails {

    String restEndpoint
    String apiKey

    FrontlineSmsDetails (String restEndpoint,String apiKey){
        this.restEndpoint = restEndpoint
        this.apiKey = apiKey
    }

    static constraints = {

        restEndpoint(nullable: false, blank: false)
        apiKey(nullable: false, blank: false)
    }
}
