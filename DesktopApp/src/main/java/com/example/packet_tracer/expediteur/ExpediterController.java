package com.example.packet_tracer.expediteur;

import com.example.packet_tracer.LoginController;
import com.example.packet_tracer.models.Bordoreau;
import com.example.packet_tracer.models.Client;
import com.example.packet_tracer.models.Driver;
import com.example.packet_tracer.models.Packet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.image.BufferedImage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;

import static com.example.packet_tracer.models.PacketStatus.INITIALIZED;


public class ExpediterController {

    @FXML
    private Button ButtonConfirmer;
    @FXML
    private Button refreshButton;
    @FXML
    private Button ButtonAnnuler;
    @FXML
    private Pane LabelCompte;
    @FXML
    private Pane LabelDeconnection;
    @FXML
    private Pane LabelHistorique;
    @FXML
    private Pane LabelNotification;
    @FXML
    private Pane LabelSuiver;

    private Stage stage;
    private Parent root;
    private Scene scene;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void switchToLogin(MouseEvent event) throws IOException {
        try {
            // Load login.fxml from the resources directory
            URL url = getClass().getResource("/com/example/packet_tracer/login.fxml");
            if (url == null) {
                throw new IOException("FXML file not found");
            }

            root = FXMLLoader.load(url);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the IOException here (e.g., show an error message to the user)
        }
    }
    @FXML
    private void switchToAccount(MouseEvent event) throws IOException {
        try {
            // Load login.fxml from the resources directory
            URL url = getClass().getResource("/com/example/packet_tracer/expediteur/account.fxml");
            if (url == null) {
                throw new IOException("FXML file not found");
            }

            root = FXMLLoader.load(url);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the IOException here (e.g., show an error message to the user)
        }
    }
    @FXML
    private void switchToNotification(MouseEvent event) throws IOException {
        try {
            // Load login.fxml from the resources directory
            URL url = getClass().getResource("/com/example/packet_tracer/expediteur/notification.fxml");
            if (url == null) {
                throw new IOException("FXML file not found");
            }

            root = FXMLLoader.load(url);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the IOException here (e.g., show an error message to the user)
        }
    }
    @FXML
    private void switchToSuiver(MouseEvent event) throws IOException {
        try {
            // Load login.fxml from the resources directory
            URL url = getClass().getResource("/com/example/packet_tracer/expediteur/suiver.fxml");
            if (url == null) {
                throw new IOException("FXML file not found");
            }

            root = FXMLLoader.load(url);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the IOException here (e.g., show an error message to the user)
        }
    }
    @FXML
    private void switchToHistorique(MouseEvent event) throws IOException {
        try {
            // Load login.fxml from the resources directory
            URL url = getClass().getResource("/com/example/packet_tracer/expediteur/historique.fxml");
            if (url == null) {
                throw new IOException("FXML file not found");
            }

            root = FXMLLoader.load(url);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the IOException here (e.g., show an error message to the user)
        }
    }
    @FXML
    private void switchToLivreur(MouseEvent event) throws IOException {
        try {
            // Load login.fxml from the resources directory
            URL url = getClass().getResource("/com/example/packet_tracer/expediteur/livreur.fxml");
            if (url == null) {
                throw new IOException("FXML file not found");
            }

            root = FXMLLoader.load(url);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the IOException here (e.g., show an error message to the user)
        }
    }

    //------------------------------------------------------------------------------------------------
    //------------------------choice box--------------------------------------------------------------

    @FXML
    private ComboBox<String> driverComboBox;

    private ObservableList<Driver1> driverList = FXCollections.observableArrayList(); // Initialize driverList;
    @FXML
    public void initialize() {
        getalldriver();
        populateDriverComboBox();
    }
    @FXML
    private void handleRefreshButton(ActionEvent event) {
        // Call the method to fetch all drivers from the database again
        driverComboBox.getItems().clear();
        driverComboBox.setPromptText("Select a Livreur");
        getalldriver();
        populateDriverComboBox();
    }

    private void populateDriverComboBox() {
        // Check if driverList is not null before populating the ComboBox
        if (driverList != null) {
            // Loop through the driverList and add cinDriver values to the ComboBox
            for (Driver1 driver : driverList) {
                String cinDriverString = String.valueOf(driver.getCinDriver());
                driverComboBox.getItems().add(cinDriverString);
            }
        }
    }
    @FXML
    void getalldriver() {
        String endpointUrl = "http://localhost:8080/api/drivers";
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointUrl))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(jsonBody -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        List<Driver1> drivers = mapper.readValue(jsonBody, new TypeReference<List<Driver1>>(){});

                        driverList.clear();
                        driverList.addAll(drivers);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

    }


    public static class Driver1 {
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String email;
        private String role;
        private String dateOfBirth;
        private String cinDriver;
        private String licenseNumber;
        private String licensePlate;
        private String brand;
        private List<Object> packets;  // Change Object to your specific packet class if you have one
        private boolean active;

        // Constructor
        public Driver1() {}

        // Getters and Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getCinDriver() {
            return cinDriver;
        }

        public void setCinDriver(String cinDriver) {
            this.cinDriver = cinDriver;
        }

        public String getLicenseNumber() {
            return licenseNumber;
        }

        public void setLicenseNumber(String licenseNumber) {
            this.licenseNumber = licenseNumber;
        }

        public String getLicensePlate() {
            return licensePlate;
        }

        public void setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public List<Object> getPackets() {
            return packets;
        }

        public void setPackets(List<Object> packets) {
            this.packets = packets;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }

    //--------------------------------------------------------------------------------
    //-------QR CODE SCAN -----------------------------------------------------------

    @FXML
    private TextArea resultTextArea;

    @FXML
    private Label statusLabel;
    @FXML
    private ImageView selectedImageView;

    private File selectedImageFile;
    private String decoded;

    public void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Image");
        selectedImageFile = fileChooser.showOpenDialog(new Stage());
        if (selectedImageFile != null) {
            try {
                Image image = new Image(selectedImageFile.toURI().toString());
                selectedImageView.setImage(image);
                statusLabel.setText("Image selected: " + selectedImageFile.getName());
            } catch (Exception e) {
                e.printStackTrace();
                statusLabel.setText("Error loading image.");
            }
        } else {
            statusLabel.setText("No image selected.");
        }
    }
    public void scanQRCode() {
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            decodeQRCode(fromImageToBuff(image));
        } else {
            resultTextArea.setText("Please select an image first.");
        }
    }

    private void decodeQRCode(BufferedImage bufferedImage) {
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            // Decode QR code from binary bitmap
            MultiFormatReader reader = new MultiFormatReader();
            Result result = reader.decode(binaryBitmap);
            decoded = result.getText();
            resultTextArea.setText(result.getText());
        } catch (NotFoundException e) {
            resultTextArea.setText("QR Code not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private BufferedImage fromImageToBuff(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixelWriter.setArgb(x, y, pixelReader.getArgb(x, y));
            }
        }

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bufferedImage.setRGB(x, y, writableImage.getPixelReader().getArgb(x, y));
            }
        }

        return bufferedImage;
    }


    //-------------------------------------------------------------------------------------------
    //---------parsing json to json from qr code (string)-----------------------------------------

    private Bordoreau convertQRcodeToObjects(String dataString) {
        String[] parts = dataString.replaceAll("[{}]", "").split(",");

        // Check if the input string has at least 5 parts
        if (parts.length < 5) {
            System.out.println(parts);
            System.out.println("------------- it has to be more than 5 parts !!");
            return null;
        }

        Long idBordoreau = Long.parseLong(parts[0].trim());
        String dateStr = parts[1].trim();
        String codeLibreur = parts[2].trim();
        String codeSecteur = parts[3].trim();

        // Assuming dateStr is in the format "YYMMDD"
        int year = Integer.parseInt(dateStr.substring(0, 2));
        int month = Integer.parseInt(dateStr.substring(2, 4));
        int day = Integer.parseInt(dateStr.substring(4, 6));
        // Create a date string in the format "yyyy-MM-dd"
        String formattedDateStr = String.format("20%02d-%02d-%02d", year, month, day);

        // Convert to LocalDateTime
        LocalDateTime date = LocalDateTime.of(2000 + year, month, day, 0, 0); // Assuming time is not provided
        // Convert LocalDateTime to Date
        ZoneId zoneId = ZoneId.systemDefault(); // or ZoneId.of("Z") for UTC
        Instant instant = date.atZone(zoneId).toInstant();
        Date date1 = Date.from(instant);


        List<Packet> packets = new ArrayList<>();
        for (int i = 4; i < parts.length; i = i + 4) {
            Long bL = Long.parseLong(parts[i].trim());
            String codeClient = parts[i + 1].trim();
            int colis = Integer.parseInt(parts[i + 2].trim());
            int sachets = Integer.parseInt(parts[i + 3].trim());

            Packet packet = new Packet(bL, codeClient, colis, sachets, INITIALIZED, null, null);
            packets.add(packet);
        }

        Bordoreau bordoreau = new Bordoreau(idBordoreau, date1, codeLibreur, codeSecteur, null, packets);
        // Set the Bordoreau object for each Packet
        for (Packet packet : packets) {
            packet.setBordoreau(bordoreau.getBordoreau());
        }

        return bordoreau;
    }


    @FXML
    private void display() {
        String dataString = decoded;
        Bordoreau result = convertQRcodeToObjects(dataString);

        // Print Bordoreau details
        System.out.println("Bordoreau Details:");
        System.out.println("ID: " + result.getBordoreau());
        System.out.println("Date: " + result.getDate());
        System.out.println("Livreur: " + result.getLivreur());
        System.out.println("Secteur: " + result.getSecteur());
        System.out.println("Sender: " + result.getSender());

        // Print associated Packet details
        System.out.println("\nPacket Details:");
        List<Packet> packets = result.getPackets();
        for (Packet packet : packets) {
            System.out.println("BL: " + packet.getIdPacket());
            System.out.println("Client: " + packet.getClientCin());
            System.out.println("Colis: " + packet.getColis());
            System.out.println("Sachets: " + packet.getSachets());
            System.out.println("Bordoreau: " + packet.getBordoreau());
            System.out.println();
        }
    }

    //---------------------------------------------------------------------------------------------
    //------------------------------here we add bordoro---------------------------------------------
    private static final String BASE_URL = "http://localhost:8080/api/";

    private static final ObjectMapper objectMapper = new ObjectMapper();


    @FXML
    private void addBordoroButton(ActionEvent event){
        String dataString = decoded;
        Bordoreau result = convertQRcodeToObjects(dataString);

        display();

        sendBordoreauJson(constructBordoreauJson(result));
    }



    ///------------ the new one here --------------

    public static String constructBordoreauJson(Bordoreau bordoreau) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        rootNode.put("idBordoreau", bordoreau.getBordoreau());
        rootNode.put("date", bordoreau.getDate().toString());
        rootNode.put("codeLibreur", bordoreau.getLivreur());
        rootNode.put("codeSecteur", bordoreau.getSecteur());

        ArrayNode packetsArray = mapper.createArrayNode();
        for (Packet packet : bordoreau.getPackets()) {
            ObjectNode packetNode = mapper.createObjectNode();
            packetNode.put("idPacket", packet.getIdPacket());
            packetNode.put("cinClient", packet.getClientCin());
            packetNode.put("colis", packet.getColis());
            packetNode.put("sachets", packet.getSachets());
            packetsArray.add(packetNode);
        }

        rootNode.set("packets", packetsArray);

        try {
            return mapper.writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    @FXML
    public static void sendBordoreauJson(String jsonBordoreau) {
        String endpointUrl = "http://localhost:8080/api/bordoreaux/json"; // Replace with your actual endpoint URL

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .build();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpointUrl))
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBordoreau))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status code: " + response.statusCode());
            System.out.println("Response body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
