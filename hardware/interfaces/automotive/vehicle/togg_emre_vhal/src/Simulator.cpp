#define LOG_TAG __FILE_NAME__

#include <android-base/properties.h>
#include <utils/SystemClock.h>
#include <log/log_main.h>

#include "togg/emre/vehicle/1.0/types.h"

#include "Simulator.h"

namespace togg::emre {
namespace vehicle {
namespace V1_0 {
namespace impl {

Simulator::Simulator(vhal_v2_0::VehiclePropertyStore* propStore) 
: Runnable(std::bind(&Simulator::loop, this, _1)), mPropStore(propStore){}

std::vector<vhal_v2_0::VehiclePropConfig> Simulator::getAllPropertyConfig() const
{
    return std::vector<vhal_v2_0::VehiclePropConfig>();
}

vhal_v2_0::StatusCode Simulator::setProperty(const vhal_v2_0::VehiclePropValue& value, bool updateStatus)
{
    ALOGD("%s(): prop:%d, %d", __func__, value.prop, updateStatus);
    vhal_v2_0::StatusCode ret = vhal_v2_0::StatusCode::OK;
    mPropStore->writeValue(value, 0);
    return ret;
}

float readFromDeviceDriver() {
    const char* devicePath = "/dev/emr_vehicle";
    int fd = open(devicePath, O_RDONLY);
    if (fd < 0) {
        //LOG(ERROR) << "Failed to open device: " << devicePath;
        ALOGE("Failed to open device: /dev/emr_vehicle");
        return 3.0f;
    }

    char buffer[32];
    float value = 3.0f;

    lseek(fd, 0, SEEK_SET);
    ssize_t bytesRead = read(fd, buffer, sizeof(buffer) - 1);
    if (bytesRead > 0) {
        buffer[bytesRead] = '\0';

        std::stringstream ss(buffer);
        if (ss >> value) {
            
        } else {
            ALOGE("Failed to open device: /dev/emr_vehicle");
            value = 4.0f;
        }
    } else {
        value = 2.0f;
    }

    close(fd);
    return value;
}

void Simulator::loop(const std::atomic_bool& request_to_stop)
{
    vhal_v2_0::VehiclePropValue value;

    for (;;)
    {
        if (request_to_stop)
        {
            break;
        }
        
        vhal_v2_0::VehiclePropValue prop;
        prop.prop = (int) VehicleProperty::PERF_VEHICLE_SPEED;
        prop.value.floatValues.resize(1);
        prop.value.floatValues[0] = readFromDeviceDriver(); // Default float value
        mPropStore->writeValue(prop, 0);

        std::this_thread::sleep_for(100ms);
    }
}

} // namespace impl
} // namespace V1_0
} // namespace vehicle
} // namespace emre
