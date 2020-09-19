DESCRIPTION = "RPMSG benchmark tool that includes both the CM4 firmware and CA application. \
	There is post on how to use this firmware here: https://www.stupid-projects.com/using-elastic-stack-elk-on-embedded-part-2/"
AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit cmake

SRC_URI = "git://dimtass@bitbucket.org/dimtass/stm32mp1-rpmsg-adcsampler.git;protocol=https"

# Modify these as desired
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

# NOTE: no Makefile found, unable to determine what needs to be done

do_configure () {
	# Specify any needed configure commands here
	:
}


DEPENDS += " gcc-arm-none-eabi-native coreutils-native"

do_compile () {
	# Specify compilation commands here
	cd ${S}
	TOOLCHAIN_DIR=${RECIPE_SYSROOT_NATIVE}/${datadir}/gcc-arm-none-eabi SRC=src_hal ./build.sh
}

do_install () {
	install -d ${D}${bindir}/
	install -m 0755 ${S}/fw_cortex_m4.sh ${D}${bindir}/

	install -d ${D}/lib/firmware/
	install -m 0755 ${S}/build-stm32/src_hal/stm32mp157c-rpmsg-adcsampler.elf ${D}/lib/firmware
}

FILES_${PN} += " \
	/lib/firmware/stm32mp157c-rpmsg-adcsampler.elf \
	${bindir}/fw_cortex_m4.sh \
	"