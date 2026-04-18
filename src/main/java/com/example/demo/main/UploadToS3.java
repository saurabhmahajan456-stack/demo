package com.example.demo.main;

public class UploadToS3 {
/*
    public static void main(String[] args) {

        String bucketName = "1055-ethans-upload-test";
        String key = "test.txt";  // File name in S3
        String filePath = "C:\\Users\\Hp\\OneDrive\\Desktop\\New_File.txt";  // Local file path

        Region region = Region.AP_SOUTH_1;  // Change to your bucket region

        S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3.putObject(putObjectRequest, Paths.get(filePath));

            System.out.println("File uploaded successfully to S3 bucket: " + bucketName);

        } catch (Exception e) {
            System.err.println("Error uploading file: " + e.getMessage());
        } finally {
            s3.close();
        }
    }

 */
}
