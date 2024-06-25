package org.example;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import org.apache.commons.lang3.concurrent.TimedSemaphore;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Возможные улучшения: <br>
 * 1. Использовать Lombok, для сокращения тривиального кода. <br>
 * 2. С использованием Spring вынести из "TimedSemaphore(1, timeUnit, requestLimit)" единицу, как @Value, для соблюдения принципа открытости закрытости(SOLID).<br>
 * 3. Вместо отдельного объявления OkHttpClient и ObjectMapper, использовать объекты предоставляемые спрингом.
 */
public class CrptAPI {

    public static final String DOCUMENTS_CREATE_URL = "https://ismp.crpt.ru/api/v3/lk/documents/create";
    public OkHttpClient okHttpClient = new OkHttpClient();
    public ObjectMapper objectMapper = new ObjectMapper();
    private final TimedSemaphore semaphore;

    public CrptAPI(TimeUnit timeUnit, int requestLimit) {
        semaphore = new TimedSemaphore(1, timeUnit, requestLimit);
    }

    public void send(Document document, String signature) throws IOException, InterruptedException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                objectMapper.writeValueAsString(document));

        Request request = new Request.Builder()
                .url(DOCUMENTS_CREATE_URL)
                .post(requestBody)
                .build();

        semaphore.acquire();
        okHttpClient.newCall(request).execute();
    }

    public class Product {

        private String certificateDocument;
        private Date certificateDocumentDate;
        private String certificateDocumentNumber;
        private String ownerInn;
        private String producerInn;
        private Date productionDate;
        private String tnvedCode;
        private String uitCode;
        private String uituCode;

        public String getCertificateDocument() {
            return certificateDocument;
        }

        public void setCertificateDocument(String certificateDocument) {
            this.certificateDocument = certificateDocument;
        }

        public Date getCertificateDocumentDate() {
            return certificateDocumentDate;
        }

        public void setCertificateDocumentDate(Date certificateDocumentDate) {
            this.certificateDocumentDate = certificateDocumentDate;
        }

        public String getCertificateDocumentNumber() {
            return certificateDocumentNumber;
        }

        public void setCertificateDocumentNumber(String certificateDocumentNumber) {
            this.certificateDocumentNumber = certificateDocumentNumber;
        }

        public String getOwnerInn() {
            return ownerInn;
        }

        public void setOwnerInn(String ownerInn) {
            this.ownerInn = ownerInn;
        }

        public String getProducerInn() {
            return producerInn;
        }

        public void setProducerInn(String producerInn) {
            this.producerInn = producerInn;
        }

        public Date getProductionDate() {
            return productionDate;
        }

        public void setProductionDate(Date productionDate) {
            this.productionDate = productionDate;
        }

        public String getTnvedCode() {
            return tnvedCode;
        }

        public void setTnvedCode(String tnvedCode) {
            this.tnvedCode = tnvedCode;
        }

        public String getUitCode() {
            return uitCode;
        }

        public void setUitCode(String uitCode) {
            this.uitCode = uitCode;
        }

        public String getUituCode() {
            return uituCode;
        }

        public void setUituCode(String uituCode) {
            this.uituCode = uituCode;
        }
    }

    public class Document {

        private Description description;
        private String docId;
        private String docStatus;
        private String docType;
        private boolean importRequest;
        private String ownerInn;
        private String participantInn;
        private String producerInn;
        private Date productionDate;
        private String productionType;
        private List<Product> products;
        private Date regDate;
        private String regNumber;

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public String getDocId() {
            return docId;
        }

        public void setDocId(String docId) {
            this.docId = docId;
        }

        public String getDocStatus() {
            return docStatus;
        }

        public void setDocStatus(String docStatus) {
            this.docStatus = docStatus;
        }

        public String getDocType() {
            return docType;
        }

        public void setDocType(String docType) {
            this.docType = docType;
        }

        public boolean isImportRequest() {
            return importRequest;
        }

        public void setImportRequest(boolean importRequest) {
            this.importRequest = importRequest;
        }

        public String getOwnerInn() {
            return ownerInn;
        }

        public void setOwnerInn(String ownerInn) {
            this.ownerInn = ownerInn;
        }

        public String getParticipantInn() {
            return participantInn;
        }

        public void setParticipantInn(String participantInn) {
            this.participantInn = participantInn;
        }

        public String getProducerInn() {
            return producerInn;
        }

        public void setProducerInn(String producerInn) {
            this.producerInn = producerInn;
        }

        public Date getProductionDate() {
            return productionDate;
        }

        public void setProductionDate(Date productionDate) {
            this.productionDate = productionDate;
        }

        public String getProductionType() {
            return productionType;
        }

        public void setProductionType(String productionType) {
            this.productionType = productionType;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public Date getRegDate() {
            return regDate;
        }

        public void setRegDate(Date regDate) {
            this.regDate = regDate;
        }

        public String getRegNumber() {
            return regNumber;
        }

        public void setRegNumber(String regNumber) {
            this.regNumber = regNumber;
        }
    }

    public class Description {

        private String participantInn;

        public String getParticipantInn() {
            return participantInn;
        }

        public void setParticipantInn(String participantInn) {
            this.participantInn = participantInn;
        }
    }
}
