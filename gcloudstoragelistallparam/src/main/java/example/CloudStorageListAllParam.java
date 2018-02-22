/**
 * CloudStorageListAllParam is an example that handles Cloud Storage buckets on GCP (Google Cloud Platform)
 * List all Cloud Storage buckets and the files they contain for a Google Cloud Project.
 * The application uses Application Default Credentials through a JSON service account key for authenticating.
 * The user must provide the credentials using the application parameters.
 * You must use 2 parameters:
 * CREDENTIALS_FILE_NAME = Path and name of the JSON credential file
 * PROJECT_ID = Name of the Google Cloud Project
 */

package example;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class CloudStorageListAllParam {

    public static void main(String[] args) {
        String credentialsFile;  // Credentials file
        String projectId;        // Project ID

        if (args.length < 2) {
            System.out.println("Not enough parameters. Proper Usage is: java -jar cloudstoragelistall.jar <CREDENTIALS_FILE_NAME> <PROJECT_ID>");
            System.exit(1);
        }

        credentialsFile = args[0];
        projectId = args[1];

        System.out.println("Credentials: " + credentialsFile);
        System.out.println("Project ID: " + projectId);

        // Gets credentials
        Credentials credentials = null;
        try {
            credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Instantiates a client
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .setProjectId(projectId)
                .build()
                .getService();

        // Lists buckets
        for (Bucket bucket : storage.list().iterateAll()) {
            System.out.printf("Bucket %s:\n", bucket.getName());
            System.out.println(bucket);
            // list a bucket's blobs
            for (Blob blob : bucket.list().iterateAll()) {
                System.out.printf("File %s:\n", blob.getName());
                System.out.println(blob);
            }
        }
    }
}
