# Google Cloud Compute Engine instances Java example

This folder contains a Java application example that handles Compute Engine instances on Google Cloud Platform (GCP).

## Requirements

* You must have a [Google Cloud Platform (GCP)](http://cloud.google.com/) account.

* You can install and use the Google Cloud SDK.

  The Google Cloud SDK is a set of tools for Google Cloud Platform.
  It contains gcloud, gsutil, and bq, which you can use to access Google Compute Engine, Google Cloud Storage, Google BigQuery,
  and other products and services from the command line. You can run these tools interactively or in your automated scripts.

* This example uses the Google Cloud Client Library for Java.

  The Google Cloud Client Library for Java is the idiomatic way for Java developers to integrate with Google Cloud Platform services,
  like Cloud Datastore and Cloud Storage. You can install the package for an individual API using Maven, Gradle, SBT, etc.

* The code was written for Java 8 and Google Cloud Client Library for Java.

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
      Service account ID: gcloud-java-examples@gcloud-java-examples.iam.gserviceaccount.com)
      ```

    * Create a key as a JSON file and download it.

    * Add the Service accounts id (Ex.: gcloud-java-examples@gcloud-java-examples.iam.gserviceaccount.com) as a member of the project in the IAM.

  * Set the GOOGLE_APPLICATION_CREDENTIALS environment variable in your Operating System with the path of your JSON service account key file.

* Run the code.

  Run application:

  ```bash
  java -jar gcloudcomputeengine.jar
  ```

  You can select an option in the menu in order to run every command:

  * 1 = List all instances
  * 2 = Run new instance
  * 3 = List instance
  * 4 = Start instance
  * 5 = Stop instance
  * 6 = Reset instance
  * 7 = Delete instance

* Test the application.

  You should see the new instance and modification of states with the Google Cloud console.
