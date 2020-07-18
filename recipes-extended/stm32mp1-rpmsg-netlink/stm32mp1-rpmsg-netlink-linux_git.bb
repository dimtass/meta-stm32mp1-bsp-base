require stm32mp1-rpmsg-netlink-common.inc

S = "${WORKDIR}/git/CA7-Source/userspace"

do_compile () {
	# Specify compilation commands here
	cd ${S}
	mkdir build
	cd build
	cmake ..
	make
}

do_install () {
	install -d ${D}/home/root
	install -m 0755 ${S}/fw_cortex_m4_netlink.sh ${D}/home/root
	install -m 0755 ${S}/build/rpmsg-netlink-client ${D}/home/root
}

FILES_${PN} += " \
		/home/root/fw_cortex_m4_netlink.sh \
		/home/root/rpmsg-netlink-client \
"