# Google Cloud Storage Download Java example

This folder contains a Java application example that handles Cloud Storage buckets on Google Cloud Platform (GCP).

Download an object from a Cloud Storage bucket in a Google Cloud Project to a local file.

## Requirements

* You must have a [Google Cloud Platform (GCP)](http://cloud.google.com/) account.

* You can install and use the Google Cloud SDK.

  The Google Cloud SDK is a set of tools for Google Cloud Platform.
  It contains gcloud, gsutil, and bq, which you can use to access Google Compute Engine, Google Cloud Storage, Google BigQuery,
  and other products and services from the command line. You can run these tools interactively or in your automated scripts.

* The code was written for:
 
  *  Java 8
  *  Apache Maven 3
  *  Google Cloud Client Library for Java

* This example uses the Google Cloud Client Library for Java.

  The Google Cloud Client Library for Java is the idiomatic way for Java developers to integrate with Google Cloud Platform services,
  like Cloud Datastore and Cloud Storage. You can install the package for an individual API using Maven, Gradle, SBT, etc.

## Using the code

* Configure your Google Cloud access keys.

  The Google Cloud client library for Java allows you to use several authentication schemes.

  The application uses Application Default Credentials through a JSON service account key for authenticating.

  The credentials are taken from `GOOGLE_APPLICATION_CREDENTIALS` environment variable.

  For example:

  ```bash
  GOOGLE_APPLICATION_CREDENTIALS = /path/to/my/key.json
  ```

  Use the [Google Cloud Platform console](http://cloud.google.com/):

  * Go to the Google Cloud Project.

  * Prepare the credentials:

    * Create a Service account.

      For example:

      ```bash
      Name: gcloud-java-examples
      Role: Owner
      Email: gcloud-java-examples@gcloud-java-examples.iam.gserviceaccount.com
      ```

    * Create a key as a JSON file and download it.

    * Add the Service accounts id (Ex.: gcloud-java-examples@gcloud-java-examples.iam.gserviceaccount.com) as a member of the project in the IAM.

  * Set the GOOGLE_APPLICATION_CREDENTIALS environment variable in your Operating System with the path of your JSON service account key file.

* Run the code.

  You must provide 3 parameters, replace the values of:

  * `<BUCKET_NAME>`     by name of the bucket.
  * `<OBJECT_NAME>`     by object name in the bucket.
  * `<LOCAL_FILE_NAME>` by local file name.

  Run application:

  ```bash
  java -jar gcloudstoragedownload.jar <BUCKET_NAME> <OBJECT_NAME> <LOCAL_FILE_NAME>
  ```

* Test the application.

  You should see the new file created in your local destiny from the Google Storage bucket in a Google Cloud Project.
