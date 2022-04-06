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
            // String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()))
            //      println(restUrl)
            //    println(basicAuth)
            URL siteURL = new URL(restUrl)
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection()
            //    connection.setRequestProperty("Authorization", basicAuth)
            connection.setRequestMethod('POST')
            connection.setRequestProperty("Content-Type", "application/json; utf-8")
            connection.setDoInput(true)
            connection.setDoOutput(true);
//            connection.setConnectTimeout(3000)
            // Send post request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream())
            wr.writeBytes(object)
            wr.flush()
            wr.close()

//            connection.connect()
            code = connection.getResponseCode()
            connection.disconnect()
            if (code == 201) {
                result = "-> Green <-\t" + "Code: " + code;
            } else {
                result = "-> Yellow <-\t" + "Code: " + code;
            }
        } catch (Exception e) {
            result = "-> Red <-\t" + "Wrong domain - Exception: " + e.getMessage();
        }
        println(result)
        return result
    }
}
