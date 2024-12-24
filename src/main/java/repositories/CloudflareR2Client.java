package repositories;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

public class CloudflareR2Client {
    private final S3Client s3Client;

    /**
     * Creates a new CloudflareR2Client with the provided configuration
     */
    public CloudflareR2Client(S3Config config) {
        this.s3Client = buildS3Client(config);
    }

    /**
     * Configuration class for R2 credentials and endpoint
     */
    public static class S3Config {
        private static String accountId;
        private static String accessKey;
        private static String secretKey;
        private static String r2Endpoint;
        private static String bucketName;
        private final String endpoint;
        static {
            Properties props = new Properties();
            try {
                props.load(new FileInputStream("/Users/perepalacin/Documents/pere-repos/files-system/sos-etseib/env.properties"));
            } catch (IOException e) {
                throw new RuntimeException("Failed to read S3 Bucket configuration", e);
            }
            accountId = props.getProperty("CF_ACCOUNT_ID").trim();
            accessKey = props.getProperty("CF_ACCESS_KEY").trim();
            secretKey = props.getProperty("CF_SECRET_KEY").trim();
            r2Endpoint = props.getProperty("CF_ENDPOINT").trim();
            bucketName = props.getProperty("CF_BUCKET_NAME").trim();
        }

        public S3Config() {
            this.endpoint = String.format(r2Endpoint, accountId);
        }

        public String getAccessKey() { return accessKey; }
        public String getSecretKey() { return secretKey; }
        public String getEndpoint() { return endpoint; }
    }

    private static S3Client buildS3Client(S3Config config) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                config.getAccessKey(),
                config.getSecretKey()
        );

        S3Configuration serviceConfiguration = S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .build();

        return S3Client.builder()
                .endpointOverride(URI.create(config.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of("auto"))
                .serviceConfiguration(serviceConfiguration)
                .build();
    }

    public List<S3Object> listObjects() {
        try {
            ListObjectsV2Request request = ListObjectsV2Request.builder()
                    .bucket(S3Config.bucketName)
                    .build();

            return s3Client.listObjectsV2(request).contents();
        } catch (S3Exception e) {
            throw new RuntimeException("Failed to list objects in bucket " + S3Config.bucketName + ": " + e.getMessage(), e);
        }
    }

    public String getObjectContent(String key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(S3Config.bucketName)
                    .key(key)
                    .build();

            ResponseInputStream<GetObjectResponse> inputStream = s3Client.getObject(getObjectRequest);

            return new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .collect(Collectors.joining("\n"));

        } catch (S3Exception e) {
            throw new RuntimeException("Failed to get object '" + key + "' from bucket '"
                    + S3Config.bucketName + "': " + e.getMessage(), e);
        }
    }

    public byte[] getObjectContentAsBytes(String key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(S3Config.bucketName)
                    .key(key)
                    .build();

            ResponseInputStream<GetObjectResponse> inputStream = s3Client.getObject(getObjectRequest);

            return inputStream.readAllBytes();

        } catch (S3Exception | IOException e) {
            throw new RuntimeException("Failed to get object '" + key + "' from bucket '"
                    + S3Config.bucketName + "': " + e.getMessage(), e);
        }
    }

}