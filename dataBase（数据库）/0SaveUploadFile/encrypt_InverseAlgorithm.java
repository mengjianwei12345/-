/**
 * Integration of Inverse algorithm using
 * Robusta endpoints
 * The class perform HTTP POST Request using HttpURLConnection
 *
 * It was developed as an delivering a Hiring Task for Robusta
 *
 * @author  Abdelrahman Amer
 * @version 1.0
 * @since   2019-12-1
 *
 * @license MIT
 */







public class InverseAlgorithm implements Encryptable{
    private URL url;
    private HttpURLConnection  con;


    @Override
    public String encrypte(String text) {
        return postRequest("http://backendtask.robustastudio.com/encode",text);
    }

    @Override
    public String decrypte(String text) {
        return postRequest("http://backendtask.robustastudio.com/decode",text);
    }

    // Consume the endpoints with POST Method
    private String postRequest(String endPoint,String param){
        StringBuffer response = new StringBuffer();
        try{
            this.url = new URL(endPoint);
            this.con = (HttpURLConnection) this.url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);

            String data = "{\"string\" :\""+param+"\"}";
            DataOutputStream  writer = new DataOutputStream(con.getOutputStream());
            byte[] input = data.getBytes("utf-8");
            writer.write(input,0,input.length);
            writer.flush();
            writer.close();

            int responseCode = con.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.con.getInputStream()));
                String inputLine;


                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }

                reader.close();
                //System.out.println(response.toString());
            }else{
                System.out.println("POST request not worked");
            }
        }catch (Exception c){
            c.printStackTrace();
        }
        return response.toString();
    }
}
