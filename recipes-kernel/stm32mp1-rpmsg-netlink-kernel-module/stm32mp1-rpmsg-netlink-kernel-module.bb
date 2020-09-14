 
SUMMARY = "Example of how to build an external Linux kernel module"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRCREV = "998f61db13fa434e97119c751c6958c1eee43727"
PV = "1.0+git${SRCPV}"

SRC_URI = "git://github.com/dimtass/stm32mp1-rpmsg-netlink-example.git;protocol=https"

S = "${WORKDIR}/git/CA7-Source/kernel-driver"

inherit module

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES_${PN} += "rpmsg-netlink"