package mz.org.cmmv.backend.sms

import mz.org.fgh.cmmv.backend.messages.FrontlineSmsDetails


class RestFrontlineSms {

    RestFrontlineSms() {

    }


    static def requestSmsSender(String object,FrontlineSmsDetails frontlineSmsDetails) {
        String restUrl = frontlineSmsDetails.getRestEndpoint()
        String result = ""
        int code = 200
        try {

            URL siteURL = new URL(restUrl)
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection()
            connection.setRequestMethod('POST')
            connection.setRequestProperty("Content-Type", "application/json; utf-8")
            connection.setDoInput(true)
            connection.setDoOutput(true);
            // Send post request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream())
            wr.writeBytes(object)
            wr.flush()
            wr.close()
            code = connection.getResponseCode()
            result= code
            connection.disconnect()

        } catch (Exception e) {
            result = "-> Red <-\t" + "Wrong domain - Exception: " + e.getMessage();
        }

        return result
    }
}
