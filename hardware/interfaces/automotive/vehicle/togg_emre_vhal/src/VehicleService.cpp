#define LOG_TAG __FILE_NAME__
#include <log/log_main.h>

#include <chrono>
#include <thread>

#include <hidl/HidlTransportSupport.h>
#include <vhal_v2_0/VehicleHalManager.h>

#include "VehicleHalImpl.h"
#include "Simulator.h"

using namespace std::chrono_literals;

using namespace togg::emre::vehicle::V1_0;

using namespace android;
using namespace android::hardware;
using namespace android::hardware::automotive::vehicle::V2_0;

int main(int argc, char const* argv[])
{
    static_cast<void>(argc); // avoid compiler warnings
    static_cast<void>(argv);

    ALOGI("%s(): togg.emre.vehicle VHAL init!\n", __func__);

    auto store = std::make_unique<vhal_v2_0::VehiclePropertyStore>();

    // Reading char device values and setting them to vehicle properties
    auto hw_client = std::make_unique<impl::Simulator>(store.get());
    hw_client->start();

    auto hal = std::make_unique<impl::VehicleHalImpl>(store.get(), hw_client.get());
    auto service = std::make_unique<VehicleHalManager>(hal.get());

    configureRpcThreadpool(1, true);

    ALOGI("%s(): Registering as service...", __func__);
    status_t status = service->registerAsService();

    if (status != OK)
    {
        ALOGE("%s(): Unable to register vehicle service (%d)", __func__, status);
        return 1;
    }

    ALOGI("%s(): Vehicle service ready", __func__);
    joinRpcThreadpool();

    return 0;
}