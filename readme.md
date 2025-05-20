To create a Kafka broker server keystore and truststore, as well as a client keystore and truststore for `localhost` using `keytool` and a self-signed Certificate Authority (CA), follow these steps:

## Step 1: Create a Certificate Authority (CA) Key and Certificate

1. **Generate a private key for the CA**:
   ```bash
   openssl genpkey -algorithm RSA -out ca-key.pem -pkeyopt rsa_keygen_bits:2048
   ```

2. **Create a self-signed certificate for the CA**:
   ```bash
   openssl req -x509 -new -nodes -key ca-key.pem -sha256 -days 365 -out ca-cert.pem -subj "/CN=KafkaCA"
   ```

This will create a `ca-cert.pem` file to sign certificates for the Kafka broker and client.

## Step 2: Create Keystores for Kafka Broker and Client

### Kafka Broker Keystore

1. Generate a keystore for the Kafka broker and create a keypair, setting the Common Name (CN) to `localhost`.
   ```bash
   keytool -genkeypair -alias kafka-broker -keyalg RSA -keystore kafka-broker-keystore.jks -keysize 2048 -validity 365 -dname "CN=localhost"
   ```

### Client Keystore

1. Generate a keystore for the client and create a keypair, also with CN as `localhost`.
   ```bash
   keytool -genkeypair -alias kafka-client -keyalg RSA -keystore kafka-client-keystore.jks -keysize 2048 -validity 365 -dname "CN=localhost"
   ```

## Step 3: Generate Certificate Signing Requests (CSRs)

1. **Kafka Broker CSR**:
   ```bash
   keytool -certreq -alias kafka-broker -keystore kafka-broker-keystore.jks -file kafka-broker.csr
   ```

2. **Client CSR**:
   ```bash
   keytool -certreq -alias kafka-client -keystore kafka-client-keystore.jks -file kafka-client.csr
   ```

## Step 4: Sign the CSRs with the CA

1. **Sign the Kafka Broker Certificate**:
   ```bash
   openssl x509 -req -in kafka-broker.csr -CA ca-cert.pem -CAkey ca-key.pem -CAcreateserial -out kafka-broker-cert.pem -days 365 -sha256
   ```

2. **Sign the Client Certificate**:
   ```bash
   openssl x509 -req -in kafka-client.csr -CA ca-cert.pem -CAkey ca-key.pem -CAcreateserial -out kafka-client-cert.pem -days 365 -sha256
   ```

## Step 5: Create Truststores and Import the CA Certificate

1. **Create Kafka Broker Truststore**:
   ```bash
   keytool -import -alias ca-cert -file ca-cert.pem -keystore kafka-broker-truststore.jks -noprompt
   ```

2. **Create Client Truststore**:
   ```bash
   keytool -import -alias ca-cert -file ca-cert.pem -keystore kafka-client-truststore.jks -noprompt
   ```

## Step 6: Import the Certificates into Keystores

1. **Import the CA Certificate into the Kafka Broker Keystore**:
   ```bash
   keytool -import -alias ca-cert -file ca-cert.pem -keystore kafka-broker-keystore.jks -noprompt
   ```

2. **Import the Signed Kafka Broker Certificate**:
   ```bash
   keytool -import -alias kafka-broker -file kafka-broker-cert.pem -keystore kafka-broker-keystore.jks -noprompt
   ```

3. **Import the CA Certificate into the Client Keystore**:
   ```bash
   keytool -import -alias ca-cert -file ca-cert.pem -keystore kafka-client-keystore.jks -noprompt
   ```

4. **Import the Signed Client Certificate**:
   ```bash
   keytool -import -alias kafka-client -file kafka-client-cert.pem -keystore kafka-client-keystore.jks -noprompt
   ```

## Step 7: Verify the Keystores and Truststores

To confirm that everything has been added properly, list the entries in each keystore and truststore:

```bash
keytool -list -v -keystore kafka-broker-keystore.jks
keytool -list -v -keystore kafka-client-keystore.jks
keytool -list -v -keystore kafka-broker-truststore.jks
keytool -list -v -keystore kafka-client-truststore.jks
```

### Summary

After these steps, you will have:

- **Kafka Broker Keystore** (`kafka-broker-keystore.jks`): Contains the broker's private key and signed certificate.
- **Kafka Broker Truststore** (`kafka-broker-truststore.jks`): Contains the CA certificate.
- **Client Keystore** (`kafka-client-keystore.jks`): Contains the client's private key and signed certificate.
- **Client Truststore** (`kafka-client-truststore.jks`): Contains the CA certificate.

These keystores and truststores are ready to be configured in your Kafka `server.properties` and client configurations for mutual TLS authentication on `localhost`.