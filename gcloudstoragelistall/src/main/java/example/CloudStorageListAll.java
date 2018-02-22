/**
 * CloudStorageListAll is an example that handles Cloud Storage buckets on GCP (Google Cloud Platform)
 * List all Cloud Storage buckets and the files they contain for a Google Cloud Project.
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

        // Lists buckets and files
        for (Bucket bucket : storage.list().iterateAll()) {
            System.out.println("Bucket: " + bucket.getName());

            System.out.println("Files in the bucket:");
            for (Blob blob : bucket.list().iterateAll()) {
                System.out.println(blob.getName());
            }
        }
    }
}
