package ru.eltech.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.eltech.client.model.Doctor;
import ru.eltech.client.request.DoctorCreateRequest;
import ru.eltech.client.request.DoctorUpdateRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DoctorRouter {

    private final String DOCTOR_URL = "http://localhost:4000/doctor";

    public DoctorRouter() {}

    public List<Doctor> getAllDoctors() throws IOException {
        URL url = new URL(DOCTOR_URL);

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
            List<Doctor> doctors = mapper.readValue(response.toString(), new TypeReference<List<Doctor>>(){});

            return doctors;
        } else {
            System.out.println("GET request error");
            return null;
        }
    }

    public Doctor getDoctorById(int id) throws IOException {
        URL url = new URL(DOCTOR_BY_ID_URL(id));

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
            Doctor doctor = mapper.readValue(response.toString(), new TypeReference<Doctor>(){});

            return doctor;
        } else {
            System.out.println("GET request error");
            return null;
        }
    }


    public Doctor createDoctor(DoctorCreateRequest request) throws IOException {
        URL url = new URL(DOCTOR_URL);

        //create params
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("name", request.getName());
        params.put("license", request.getLicense());

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
            Doctor doctor = mapper.readValue(response.toString(), new TypeReference<Doctor>(){});

            return doctor;
        } else {
            System.out.println("POST request error");
            return new Doctor();
        }
    }

    public Doctor updateDoctor(DoctorUpdateRequest request) throws IOException {
        URL url = new URL(DOCTOR_BY_ID_URL(request.getId()));

        //create params
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("name", request.getName());
        params.put("license", request.getLicense());

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
            Doctor doctor = mapper.readValue(response.toString(), new TypeReference<Doctor>(){});

            return doctor;
        } else {
            System.out.println("POST request error");
            return new Doctor();
        }
    }

    public void deleteDoctor(int id) throws IOException {
        URL url = new URL(DOCTOR_DELETE_URL(id));

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("success");
        } else {
            System.out.println("DELETE request error");
        }
    }

    private String DOCTOR_BY_ID_URL(int id) { return DOCTOR_URL + "/" + id; }

    private String DOCTOR_DELETE_URL(int id) { return DOCTOR_URL + "/delete/" + id; }
}
