BSP base layer for the STM32MP1
----

This layer just makes a bit easier to use the `meta-st-openlinux` and `meta-st-stm32mp` layers
with `poky` and build images. There is a script that automates the yocto setup.

This layer also adds an extra machine which is based on the `stm32mp1-disco` and it's named
`stm32mp1-discotest`.

## How to use this layer
To build the image you need to use the `repo` tool and the `manifest.xml` in this repo.

#### Clone all repos
In order to fetch all the needed repos then create a new folder (e.g. stm32mp1) and then
inside that folder create this path `.repo/manifests`.

```sh
mkdir -p stm32mp1-yocto/.repo/manifests
cd stm32mp1-yocto
```

Now copy the `manifest.xml` file in `stm32mp1-yocto/.repo/manifests`. Next inside the
`stm32mp1-yocto` directory run:

```sh
repo init -u https://bitbucket.org/dimtass/meta-stm32mp1-bsp-base/src/master/default.xml
repo sync
```

After the repo tool is finished then you should find all the meta layers in the `sources/`
directory.

#### Setting the environment
Then from the top directory run these commands:

```sh
ln -s sources/meta-stm32mp1-bsp-base/scripts/setup-environment.sh .
```

Then run this command to build an image that supports qt5 and eglfs. You may not really need
a GUI, but by default I use it for my convenience.

```sh
MACHINE=stm32mp1-discotest DISTRO=openstlinux-eglfs source ./setup-environment.sh build
```

Then build the image
```sh
bitbake stm32mp1-qt-eglfs-image
```

If you need the SDK then run:
```sh
bitbake -c populate_sdk stm32mp1-qt-eglfs-image
```

## Flash the image on the target
To flash the image to the target you need to use the `STM32CubeProgrammer` and place the `BOOT0`
and `BOOT1` switches to `OFF`, then connect a USB cable from your host to the USB connector near
the HDMI connector and run the `STM32CubeProgrammer` like this:

```sh
cd build/tmp-glibc/deploy/images/stm32mp1-discotest/flashlayout_stm32mp1-qt-eglfs-image
sudo '/opt/STM32CubeProgrammer/bin/STM32_Programmer_CLI' -c port=usb1 -w FlashLayout_sdcard_stm32mp157c-dk2-trusted.tsv
```

The above command assumes that the `STM32CubeProgrammer` is installed in the `/opt` path. You can
also use the gui if you like, but this method might be more convenient, especially if the gui doesn't
run on your host for some reason (there are many complains about the gui in Linux).

## Test EGLFS
To test that eglfs and the display are working properly then boot the board and then in the console
run these commands;

```sh 
cd /usr/share/examples/gui/openglwindow
export QT_QPA_EGLFS_ALWAYS_SET_MODE="1"
./openglwindow
```

You can also test the gpu with `glmark2` like this:

```sh
glmark2-es2-drm
```

## Bonus material
I've also added two recipes in `meta-stm32mp1-bsp-base/recipes-extended/stm32mp1-rpmsg-test`.
These recipes can be used to build a cmake firmware for the CM4 and a Linux application for
the CPU that benchmark the OpenAMP, which the IPC which is used for communication between the
CM4 (Cortex-M4) and the CA7 (Cortex-A7). By default those recipes are added in the `stm32mp1-qt-eglfs-image`
image.

The CM4 firmware is located in `/lib/firmware/stm32mp157c-rpmsg-test.elf`. There is also a script
that loads the firmware in the CM4 and it's located in `/home/root/fw_cortex_m4.sh`. Finally,
there's an app that tests the OpenAMP and it's located in `/home/root/tty-test-client`.

To test the OpenAMP run those commands on the board's console:
```sh
cd /home/root
./fw_cortex_m4.sh start             # This loads the CM4 firmware
./tty-test-client /dev/ttyRPMSG0    # Runs the test
./fw_cortex_m4.sh stop              # This unloads the CM4 firmware
```

This is a sample output of the benchmark tool
```
- 19:46:58.168 INFO: Application started
- 19:46:58.169 INFO: Connected to /dev/ttyRPMSG0
- 19:46:58.176 INFO: Initialized buffer with CRC16: 0x1818
- 19:46:58.177 INFO: ---- Creating tests ----
- 19:46:58.177 INFO: -> Add test: block=512, blocks: 1
- 19:46:58.177 INFO: -> Add test: block=512, blocks: 2
- 19:46:58.177 INFO: -> Add test: block=512, blocks: 4
- 19:46:58.177 INFO: -> Add test: block=512, blocks: 8
- 19:46:58.177 INFO: -> Add test: block=1024, blocks: 1
- 19:46:58.177 INFO: -> Add test: block=1024, blocks: 2
- 19:46:58.177 INFO: -> Add test: block=1024, blocks: 4
- 19:46:58.177 INFO: -> Add test: block=1024, blocks: 5
- 19:46:58.177 INFO: -> Add test: block=2048, blocks: 1
- 19:46:58.177 INFO: -> Add test: block=2048, blocks: 2
- 19:46:58.177 INFO: -> Add test: block=4096, blocks: 1
- 19:46:58.177 INFO: ---- Starting tests ----
- 19:46:58.189 INFO: -> b: 512, n:1, nsec: 11840174, bytes sent: 512
- 19:46:58.208 INFO: -> b: 512, n:2, nsec: 18479159, bytes sent: 1024
- 19:46:58.239 INFO: -> b: 512, n:4, nsec: 31140586, bytes sent: 2048
- 19:46:58.296 INFO: -> b: 512, n:8, nsec: 56413315, bytes sent: 4096
- 19:46:58.311 INFO: -> b: 1024, n:1, nsec: 15380189, bytes sent: 1024
- 19:46:58.336 INFO: -> b: 1024, n:2, nsec: 24917144, bytes sent: 2048
- 19:46:58.381 INFO: -> b: 1024, n:4, nsec: 43986764, bytes sent: 4096
- 19:46:58.434 INFO: -> b: 1024, n:5, nsec: 53546303, bytes sent: 5120
- 19:46:58.456 INFO: -> b: 2048, n:1, nsec: 21874506, bytes sent: 2048
- 19:46:58.494 INFO: -> b: 2048, n:2, nsec: 37771655, bytes sent: 4096
- 19:46:58.532 INFO: -> b: 4096, n:1, nsec: 37891197, bytes sent: 4096
```

For more details browse the code in the recipe's url repo.

## Author
Dimitris Tassopoulos <dimtass@gmail.com>