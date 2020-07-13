require stm32mp1-rpmsg-common.inc

DEPENDS += " gcc-arm-none-eabi-native coreutils-native"

do_compile () {
	# Specify compilation commands here
	cd ${S}
	TOOLCHAIN_DIR=${RECIPE_SYSROOT_NATIVE}/${datadir}/gcc-arm-none-eabi SRC=src_hal ./build.sh
}

do_install () {
	install -d ${D}/lib/firmware/
	install -m 0755 ${S}/build-stm32/src_hal/stm32mp157c-rpmsg-test.elf ${D}/lib/firmware
}

FILES_${PN} += "/lib/firmware/stm32mp157c-rpmsg-test.elf"