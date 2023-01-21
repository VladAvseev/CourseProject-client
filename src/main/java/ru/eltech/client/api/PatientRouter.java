package ru.eltech.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.eltech.client.model.Patient;
import ru.eltech.client.request.PatientCreateRequest;
import ru.eltech.client.request.PatientUpdateRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PatientRouter {

    private final String PATIENT_URL = "http://localhost:4000/patient";

    public PatientRouter() {}

    public List<Patient> getAllPatients() throws IOException {
        URL url = new URL(PATIENT_URL);

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
            List<Patient> patients = mapper.readValue(response.toString(), new TypeReference<List<Patient>>(){});

            return patients;
        } else {
            System.out.println("GET request error");
            return null;
        }
    }

    public Patient getPatientById(int id) throws IOException {
        URL url = new URL(PATIENT_BY_ID_URL(id));

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
            Patient patient = mapper.readValue(response.toString(), new TypeReference<Patient>(){});

            return patient;
        } else {
            System.out.println("GET request error");
            return null;
        }
    }


    public Patient createPatient(PatientCreateRequest request) throws IOException {
        URL url = new URL(PATIENT_URL);

        //create params
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("name", request.getName());
        params.put("phone", request.getPhone());
        params.put("email", request.getEmail());

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
            Patient patient = mapper.readValue(response.toString(), new TypeReference<Patient>(){});

            return patient;
        } else {
            System.out.println("POST request error");
            return new Patient();
        }
    }

    public Patient updatePatient(PatientUpdateRequest request) throws IOException {
        URL url = new URL(PATIENT_BY_ID_URL(request.getId()));

        //create params
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("name", request.getName());
        params.put("phone", request.getPhone());
        params.put("email", request.getEmail());

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
            Patient patient = mapper.readValue(response.toString(), new TypeReference<Patient>(){});

            return patient;
        } else {
            System.out.println("POST request error");
            return new Patient();
        }
    }

    public void deletePatient(int id) throws IOException {
        URL url = new URL(PATIENT_DELETE_URL(id));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("success");
        } else {
            System.out.println("DELETE request error");
        }
    }

    private String PATIENT_BY_ID_URL(int id) { return PATIENT_URL + "/" + id; }

    private String PATIENT_DELETE_URL(int id) { return PATIENT_URL + "/delete/" + id; }
}
