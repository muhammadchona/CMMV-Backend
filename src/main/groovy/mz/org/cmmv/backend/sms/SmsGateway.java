package mz.org.cmmv.backend.sms;

public class SmsGateway {

    private String destination;
    private String text;

    private String service;


    public SmsGateway() {

    }
    public SmsGateway(String destination, String text, String service) {
        this.destination = destination;
        this.text = text;
        this.service = service;
    }

    public String getDestination() {
        return destination;
    }

    public String getText() {
        return text;
    }

    public String getService() {
        return service;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setService(String service) {
        this.service = service;
    }
}
