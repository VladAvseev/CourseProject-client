package ru.eltech.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.eltech.client.model.Ticket;
import ru.eltech.client.request.TicketCreateRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TicketRouter {

    private final String TICKET_URL = "http://localhost:4000/ticket";

    public TicketRouter() {}

    public List<Ticket> getAllTickets() throws IOException {
        URL url = new URL(TICKET_URL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ObjectMapper mapper = new ObjectMapper();
            List<Ticket> tickets = mapper.readValue(response.toString(), new TypeReference<List<Ticket>>(){});

            return tickets;
        } else {
            System.out.println("GET request error");
            return null;
        }
    }

    public Ticket createTicket(TicketCreateRequest request) throws IOException {
        URL url = new URL(TICKET_URL);

        //create params
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("doctorId", request.getDoctorId());
        params.put("patientId", request.getPatientId());
        params.put("diagnosis", request.getDiagnosis());

        //add params to request
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        //add headers to request
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        connection.setDoOutput(true);
        connection.getOutputStream().write(postDataBytes);

        //get response
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ObjectMapper mapper = new ObjectMapper();
            Ticket ticket = mapper.readValue(response.toString(), new TypeReference<Ticket>(){});

            return ticket;
        } else {
            System.out.println("POST request error");
            return new Ticket();
        }
    }

    public void deleteTicket(int id) throws IOException {
        URL url = new URL(TICKET_DELETE_URL(id));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("success");
        } else {
            System.out.println("DELETE request error");
        }
    }

    private String TICKET_DELETE_URL(int id) { return TICKET_URL + "/delete/" + id; }
}
