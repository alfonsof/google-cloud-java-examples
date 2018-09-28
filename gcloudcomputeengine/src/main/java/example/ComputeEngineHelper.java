/**
 * ComputeEngineSHelper class with methods for managing Google Cloud Compute Engine VM instances.
 */

package example;

import java.util.Iterator;
import java.util.concurrent.TimeoutException;
import com.google.cloud.Page;
import com.google.cloud.compute.Compute;
import com.google.cloud.compute.ComputeOptions;
import com.google.cloud.compute.Operation;
import com.google.cloud.compute.RegionAddressId;
import com.google.cloud.compute.AddressInfo;
import com.google.cloud.compute.Instance;
import com.google.cloud.compute.ImageId;
import com.google.cloud.compute.DiskId;
import com.google.cloud.compute.DiskInfo;
import com.google.cloud.compute.ImageDiskConfiguration;
import com.google.cloud.compute.Address;
import com.google.cloud.compute.InstanceId;
import com.google.cloud.compute.NetworkId;
import com.google.cloud.compute.AttachedDisk;
import com.google.cloud.compute.AttachedDisk.PersistentDiskConfiguration;
import com.google.cloud.compute.AttachedDisk.CreateDiskConfiguration;
import com.google.cloud.compute.NetworkInterface;
import com.google.cloud.compute.NetworkInterface.AccessConfig;
import com.google.cloud.compute.MachineTypeId;
import com.google.cloud.compute.InstanceInfo;


public final class ComputeEngineHelper {

    private static final String IMAGE_NAME          = "ubuntu-1604-xenial-v20180222"; // Image name
    private static final String IMAGE_PROJECT_NAME  = "ubuntu-os-cloud";              // Image project name
    private static final String INSTANCE_TYPE       = "n1-standard-1";                // Instance type
    private static final String REGION_NAME         = "us-east1";                     // GCP Region
    private static final String ZONE_NAME           = "us-east1-b";                   // GCP Zone
    private static final String INSTANCE_NAME       = "my-instance";                  // Name of the instance
    private static final String DISK_NAME           = "my-disk";                      // Name of the disk
    private static final String DEVICE_NAME         = "dev0";                         // Name of the disk device
    private static final String REGION_ADDRESS_NAME = "my-address";                   // Name of the region address
    private static final String NETWORK_NAME        = "default";                      // Name of the network

    private ComputeEngineHelper() {
    }


    /**
     * List Compute Engine VM instances
     */
    public static void listInstances() {

        Page<Instance> instancePage;

        // Instantiate a client
        Compute compute = ComputeOptions.getDefaultInstance().getService();

        System.out.println("Listing VM instances ...");

        // List instances
        instancePage = compute.listInstances();

        System.out.printf("Instances:\n");

        Iterator<Instance> iterator = instancePage.iterateAll();
        while (iterator.hasNext()) {
            Instance instance = iterator.next();
            System.out.printf("Instance: %s\n", instance.getInstanceId().getInstance());
            System.out.printf("Data instance:\n%s\n\n", instance);
        }
    }


    /**
     * Create a Compute Engine VM instance
     * Create an instance and create a boot disk on the fly
     */
    public static InstanceId createInstance() {
        // Instantiate a client
        Compute compute = ComputeOptions.getDefaultInstance().getService();

        System.out.println("Creating VM instance ...");

        // Create a Compute Engine VM instance
        InstanceId instanceId = InstanceId.of(ZONE_NAME, INSTANCE_NAME);
        NetworkId networkId = NetworkId.of(NETWORK_NAME);
        ImageId imageId = ImageId.of(IMAGE_PROJECT_NAME, IMAGE_NAME);
        AttachedDisk attachedDisk = AttachedDisk.of(CreateDiskConfiguration.newBuilder(imageId)
                        .setAutoDelete(true)
                        .build());
        NetworkInterface networkInterface = NetworkInterface.of(networkId);
        MachineTypeId machineTypeId = MachineTypeId.of(ZONE_NAME, INSTANCE_TYPE);
        InstanceInfo instance =
                InstanceInfo.of(instanceId, machineTypeId, attachedDisk, networkInterface);
        Operation operation = compute.create(instance);
        // Wait for operation to complete
        try {
            operation = operation.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        if (operation.getErrors() == null) {
            System.out.println("Created, instance: " + instanceId);
        } else {
            // Inspect operation.getErrors()
            throw new RuntimeException("Error: Instance creation failed.");
        }
        return instanceId;
    }


    /**
     * Create a Compute Engine VM instance in another way
     * Create an instance, create a boot disk and an external IP address
     */
    public static InstanceId createInstanceDiskAndAddress() {
        // Instantiate a client
        Compute compute = ComputeOptions.getDefaultInstance().getService();

        System.out.println("Creating VM instance ...");

        // Create an external region IP address
        RegionAddressId addressId = createRegionIpAddress(compute);

        // Create a persistent disk
        DiskId diskId = CreatePersistentDisk(compute);

        // Create a Compute Engine VM instance
        Address externalIp = compute.getAddress(addressId);
        InstanceId instanceId = InstanceId.of(ZONE_NAME, INSTANCE_NAME);
        NetworkId networkId = NetworkId.of(NETWORK_NAME);
        AttachedDisk.PersistentDiskConfiguration attachConfiguration =
                PersistentDiskConfiguration.newBuilder(diskId).setBoot(true).build();
        AttachedDisk attachedDisk = AttachedDisk.of(DEVICE_NAME, attachConfiguration);
        // Create static external address
        NetworkInterface networkInterface = NetworkInterface.newBuilder(networkId)
                .setAccessConfigurations(AccessConfig.of(externalIp.getAddress()))
                .build();
        MachineTypeId machineTypeId = MachineTypeId.of(ZONE_NAME, INSTANCE_TYPE);
        InstanceInfo instance =
                InstanceInfo.of(instanceId, machineTypeId, attachedDisk, networkInterface);
        Operation operation = compute.create(instance);
        // Wait for operation to complete
        try {
            operation = operation.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        if (operation.getErrors() == null) {
            System.out.println("Created, instance: " + instanceId);
        } else {
            // Inspect operation.getErrors()
            throw new RuntimeException("Error: Instance creation failed.");
        }
        return instanceId;
    }


    /**
     * List a Compute Engine VM instance
     */
    public static void listInstance(InstanceId instanceId) {

        if (instanceId == null) {
            System.out.println("Error: NO Instance.");
            return;
        }

        // Instantiate a client
        Compute compute = ComputeOptions.getDefaultInstance().getService();

        System.out.println("Listing VM instance ...");

        // Get instance
        Instance instance = compute.getInstance(instanceId);

        if (instanceId == null) {
            System.out.printf("Error: Instance \"%s\" does NOT exist.\n", instanceId);
        } else {
            System.out.printf("Instance: %s\n", instanceId.getInstance());
            System.out.printf("Data instance:\n%s\n\n", instance);
        }
    }


    /**
     * Start a Compute Engine VM instance
     */
    public static void startInstance(InstanceId instanceId) {

        if (instanceId == null) {
            System.out.println("Error: NO Instance.");
            return;
        }

        // Instantiate a client
        Compute compute = ComputeOptions.getDefaultInstance().getService();

        System.out.println("Starting VM instance ...");

        // Start instance
        Operation operation = compute.start(instanceId);

        if (operation == null) {
            System.out.printf("Error: Instance \"%s\" does NOT exist.\n", instanceId);
            return;
        }

        // Wait for operation to complete
        try {
            operation = operation.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        if (operation.getErrors() == null) {
            System.out.println("Started, instance: " + instanceId);
        } else {
            // Inspect operation.getErrors()
            throw new RuntimeException("Error: Instance start failed.");
        }
    }


    /**
     * Stop a Compute Engine VM instance
     */
    public static void stopInstance(InstanceId instanceId) {

        if (instanceId == null) {
            System.out.println("Error: NO Instance.");
            return;
        }

        // Instantiate a client
        Compute compute = ComputeOptions.getDefaultInstance().getService();

        System.out.println("Stopping VM instance ...");

        // Stop instance
        Operation operation = compute.stop(instanceId);

        if (operation == null) {
            System.out.printf("Error: Instance \"%s\" does NOT exist.\n", instanceId);
            return;
        }

        // Wait for operation to complete
        try {
            operation = operation.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        if (operation.getErrors() == null) {
            System.out.println("Stopped, instance: " + instanceId);
        } else {
            // Inspect operation.getErrors()
            throw new RuntimeException("Error: Instance stop failed.");
        }
    }


    /**
     * Reset a Compute Engine VM instance
     */
    public static void resetInstance(InstanceId instanceId) {

        if (instanceId == null) {
            System.out.println("Error: NO Instance.");
            return;
        }

        // Instantiate a client
        Compute compute = ComputeOptions.getDefaultInstance().getService();

        System.out.println("Resetting VM instance ...");

        // Reset instance
        Operation operation = compute.reset(instanceId);

        if (operation == null) {
            System.out.printf("Error: Instance \"%s\" does NOT exist.\n", instanceId);
            return;
        }

        // Wait for operation to complete
        try {
            operation = operation.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        if (operation.getErrors() == null) {
            System.out.println("Reset, instance: " + instanceId);
        } else {
            // Inspect operation.getErrors()
            throw new RuntimeException("Error: Instance reset failed.");
        }
    }


    /**
     * Delete a Compute Engine VM instance
     */
    public static void deleteInstance(InstanceId instanceId) {

        if (instanceId == null) {
            System.out.println("Error: NO Instance.");
            return;
        }

        // Instantiate a client
        Compute compute = ComputeOptions.getDefaultInstance().getService();

        System.out.println("Deleting VM instance ...");

        // Delete instance
        Operation operation = compute.deleteInstance(instanceId);

        if (operation == null) {
            System.out.printf("Error: Instance \"%s\" does NOT exist.\n", instanceId);
            return;
        }

        // Wait for operation to complete
        try {
            operation = operation.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        if (operation.getErrors() == null) {
            System.out.println("Deleted, instance: " + instanceId);
        } else {
            // Inspect operation.getErrors()
            throw new RuntimeException("Error: Instance deletion failed.");
        }
    }


    /**
     * Create an external region IP address
     */
    private static RegionAddressId createRegionIpAddress(Compute compute) {
        System.out.println("Creating Region IP Address ...");
        RegionAddressId addressId = RegionAddressId.of(REGION_NAME, REGION_ADDRESS_NAME);
        Operation operation = compute.create(AddressInfo.of(addressId));
        // Wait for operation to complete
        try {
            operation = operation.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        if (operation.getErrors() == null) {
            System.out.println("Created, Region IP Address: " + addressId);
        } else {
            // Inspect operation.getErrors()
            throw new RuntimeException("Error: Address creation failed.");
        }

        return addressId;
    }


    /**
     * Create a persistent disk
     */
    private static DiskId CreatePersistentDisk(Compute compute) {

        System.out.println("Creating Persistent Disk ...");
        ImageId imageId = ImageId.of(IMAGE_PROJECT_NAME, IMAGE_NAME);
        DiskId diskId = DiskId.of(ZONE_NAME, DISK_NAME);
        ImageDiskConfiguration diskConfiguration = ImageDiskConfiguration.of(imageId);
        DiskInfo disk = DiskInfo.of(diskId, diskConfiguration);
        Operation operation = compute.create(disk);
        // Wait for operation to complete
        try {
            operation = operation.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        if (operation.getErrors() == null) {
            System.out.println("Created, Disk: " + diskId);
        } else {
            // Inspect operation.getErrors()
            throw new RuntimeException("Error: Disk creation failed.");
        }

        return diskId;
    }
}
