DESCRIPTION = "RPMSG benchmark tool that includes both the CM4 firmware and CA application. \
		There is a post on how to use this repo here: https://www.stupid-projects.com/benchmarking-the-stm32mp1-ipc-between-the-mcu-and-cpu-part-2/"
AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit cmake

SRC_URI = "git://github.com/dimtass/stm32mp1-rpmsg-netlink-example.git;protocol=https"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "998f61db13fa434e97119c751c6958c1eee43727"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

# NOTE: no Makefile found, unable to determine what needs to be done

do_configure () {
	# Specify any needed configure commands here
	:
}
