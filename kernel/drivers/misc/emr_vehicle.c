#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/fs.h>
#include <linux/uaccess.h>
#include <linux/cdev.h>
#include <linux/device.h>

#define DEVICE_NAME "emr_vehicle"
#define CLASS_NAME "emr_vehicle_class"

static int major_number;
static struct class *emr_vehicle_class = NULL;
static struct device *emr_vehicle_device = NULL;

static char emr_value[256] = "1.0\n"; // initial value
static int emr_open(struct inode *inode, struct file *file) {
    printk(KERN_INFO "emr_vehicle: Device opened\n");
    return 0;
}

static ssize_t emr_read(struct file *file, char __user *buffer, size_t len, loff_t *offset) {
    int ret = simple_read_from_buffer(buffer, len, offset, emr_value, strlen(emr_value));
    printk(KERN_INFO "emr_vehicle: Device read, value sent: %s\n", emr_value);
    return ret;
}

static ssize_t emr_write(struct file *file, const char __user *buffer, size_t len, loff_t *offset) {
    int ret = simple_write_to_buffer(emr_value, sizeof(emr_value) - 1, offset, buffer, len);
    emr_value[len] = '\0';  // Null-terminate the string
    printk(KERN_INFO "emr_vehicle: Device written, new value: %s\n", emr_value);
    return ret;
}

static int emr_release(struct inode *inode, struct file *file) {
    printk(KERN_INFO "emr_vehicle: Device closed\n");
    return 0;
}

static struct file_operations fops = {
    .owner = THIS_MODULE,
    .open = emr_open,
    .read = emr_read,
    .write = emr_write,
    .release = emr_release,
};

static int __init emr_vehicle_init(void) {
    printk(KERN_INFO "emr_vehicle: Initializing device\n");

    major_number = register_chrdev(0, DEVICE_NAME, &fops);
    if (major_number < 0) {
        printk(KERN_ALERT "emr_vehicle: Failed to register major number\n");
        return major_number;
    }
    printk(KERN_INFO "emr_vehicle: Registered with major number %d\n", major_number);

    emr_vehicle_class = class_create(THIS_MODULE, CLASS_NAME);
    if (IS_ERR(emr_vehicle_class)) {
        unregister_chrdev(major_number, DEVICE_NAME);
        printk(KERN_ALERT "emr_vehicle: Failed to create class\n");
        return PTR_ERR(emr_vehicle_class);
    }
    emr_vehicle_device = device_create(emr_vehicle_class, NULL, MKDEV(major_number, 0), NULL, DEVICE_NAME);
    if (IS_ERR(emr_vehicle_device)) {
        class_destroy(emr_vehicle_class);
        unregister_chrdev(major_number, DEVICE_NAME);
        printk(KERN_ALERT "emr_vehicle: Failed to create device\n");
        return PTR_ERR(emr_vehicle_device);
    }

    printk(KERN_INFO "emr_vehicle: Device created at /dev/%s\n", DEVICE_NAME);
    return 0;
}

static void __exit emr_vehicle_exit(void) {
    device_destroy(emr_vehicle_class, MKDEV(major_number, 0));
    class_destroy(emr_vehicle_class);
    unregister_chrdev(major_number, DEVICE_NAME);
    printk(KERN_INFO "emr_vehicle: Device unregistered\n");
}

module_init(emr_vehicle_init);
module_exit(emr_vehicle_exit);

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Emre Harbutoğlu - Case Study");
MODULE_DESCRIPTION("A char device driver for simulating car peripherals /dev/emr_vehicle");
MODULE_VERSION("1.0");
