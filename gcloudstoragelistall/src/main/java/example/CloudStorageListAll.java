/**
 * CloudStorageListAll is an example that handles Cloud Storage buckets on GCP (Google Cloud Platform).
 * List information about all Cloud Storage buckets and the objects that they contain in a Google Cloud Project.
 * The application uses Application Default Credentials through a JSON service account key for authenticating.
 * The credentials are taken from GOOGLE_APPLICATION_CREDENTIALS environment variable.
 */

package example;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class CloudStorageListAll {

    public static void main(String[] args) {

        // Instantiates a client
        Storage storage = StorageOptions.getDefaultInstance().getService();

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
