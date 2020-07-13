require stm32mp1-rpmsg-common.inc

do_compile () {
	# Specify compilation commands here
	cd ${S}/CA-source
	mkdir build
	cd build
	cmake ..
	make
}

do_install () {
	install -d ${D}/home/root
	install -m 0755 ${S}/fw_cortex_m4.sh ${D}/home/root
	install -m 0755 ${S}/CA-source/build/tty-test-client ${D}/home/root
}

FILES_${PN} += " \
		/home/root/fw_cortex_m4.sh \
		/home/root/tty-test-client \
"