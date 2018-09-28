/**
 * ComputeEngineInstances is an example that handles Compute Engine Instances on GCP (Google Cloud Platform).
 * Using the AWSHelper class.
 */

package example;

import java.io.IOException;
import java.util.Scanner;
import com.google.cloud.compute.InstanceId;

public class ComputeEngineInstances {

    /**
     * Print a menu in the screen with the available options
     */
    private static void printMenu() {
        System.out.println("\nMENU");
        System.out.println("0 = Quit");
        System.out.println("1 = List all VM instances");
        System.out.println("2 = Create VM instance");
        System.out.println("3 = List VM instance");
        System.out.println("4 = Start VM instance");
        System.out.println("5 = Stop VM instance");
        System.out.println("6 = Reset VM instance");
        System.out.println("7 = Delete VM instance");
        System.out.println("Enter an option?");
    }

    /**
     * Read from keyboard the option selected by user
     */
    private static int getOption(Scanner sc) {
        int option;

        String line = sc.nextLine();
        if (line != null && !line.isEmpty() && line.matches("[0-9]+")) {
            option = Integer.parseInt(line);
        } else {
            option = 100;
        }

        return option;
    }

    public static void main(String[] args) throws IOException {
        InstanceId instanceId = null;
        Scanner sc = new Scanner(System.in);
        int option;

        do {
            printMenu();
            option = getOption(sc);

            switch (option) {
                case 0:
                    System.out.println("\nBye");
                    break;
                case 1:  // List all instances
                    ComputeEngineHelper.listInstances();
                    break;
                case 2:  // Run instance
                    instanceId = ComputeEngineHelper.createInstance();
                    break;
                case 3:  // List instance
                    ComputeEngineHelper.listInstance(instanceId);
                    break;
                case 4:  // Start instance
                    ComputeEngineHelper.startInstance(instanceId);
                    break;
                case 5:  // Stop instance
                    ComputeEngineHelper.stopInstance(instanceId);
                    break;
                case 6:  // Reset instance
                    ComputeEngineHelper.resetInstance(instanceId);
                    break;
                case 7:  // Delete instance
                    ComputeEngineHelper.deleteInstance(instanceId);
                    instanceId = null;
                    break;
                default:
                    System.out.println("ERROR: Enter a valid option!!");
            }
        } while (option != 0);
        sc.close();
    }
}
