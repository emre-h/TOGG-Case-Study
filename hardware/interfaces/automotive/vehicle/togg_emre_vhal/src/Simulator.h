#pragma once

#include <unistd.h>
#include <fcntl.h>
#include <atomic>
#include <mutex>
#include <thread>

#include <vhal_v2_0/VehiclePropertyStore.h>
#include "VehicleHalClient.h"
#include "Runnable.h"

namespace vhal_v2_0 = android::hardware::automotive::vehicle::V2_0;

namespace togg::emre {
namespace vehicle {
using namespace utils;
namespace V1_0 {
namespace impl {

using SyncedVehiclePropValue = std::pair<vhal_v2_0::VehiclePropValue, std::mutex>;

class Simulator : public VehicleHalClient, public Runnable
{
public:
    Simulator(vhal_v2_0::VehiclePropertyStore* propStore);
    std::vector<vhal_v2_0::VehiclePropConfig> getAllPropertyConfig() const override;

    vhal_v2_0::VehiclePropertyStore* mPropStore;
    vhal_v2_0::StatusCode setProperty(const vhal_v2_0::VehiclePropValue& value, bool updateStatus) override;

private:
    void loop(const std::atomic_bool& request_to_stop);
    std::thread mThread;

    SyncedVehiclePropValue mVendorTestSysProp;
};

} // namespace impl
} // namespace V1_0
} // namespace vehicle
} // namespace emre