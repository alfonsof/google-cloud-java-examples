/**
 * CloudStorageListAllParam is an example that handles Cloud Storage buckets on GCP (Google Cloud Platform).
 * List information about all Cloud Storage buckets and the objects that they contain in a Google Cloud Project.
 * The application uses Application Default Credentials through a JSON service account key for authenticating.
 * The user must provide the credentials using the application parameters.
 * You must provide 2 parameters:
 * CREDENTIALS_FILE_NAME = Path and name of the JSON credential file
 * PROJECT_ID = Name of the Google Cloud Project
 */

package example;

import java.io.File;
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
            System.out.println("Not enough parameters.\nProper Usage is: java -jar cloudstoragelistall.jar <CREDENTIALS_FILE_NAME> <PROJECT_ID>");
            System.exit(1);
        }

        credentialsFile = args[0];
        projectId = args[1];

        System.out.println("Credentials file: " + credentialsFile);
        System.out.println("Project ID:       " + projectId);

        // Get credentials file
        File file = new File(credentialsFile);
        if (!file.exists()) {
            System.out.printf("Error: Credentials file \"%s\" does NOT exist.", credentialsFile);
            System.exit(1);
        }

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

        System.out.println("Listing Cloud Storage buckets and objects ...");
        // Lists Buckets
        for (Bucket bucket : storage.list().iterateAll()) {
            System.out.println("* Bucket: " + bucket.getName());
            // Lists Objects
            for (Blob blob : bucket.list().iterateAll()) {
                System.out.println("  - Object: " + blob.getName() + " (size = " + blob.getSize() + ")");
            }
        }
        System.out.println("Listed");
    }
}
