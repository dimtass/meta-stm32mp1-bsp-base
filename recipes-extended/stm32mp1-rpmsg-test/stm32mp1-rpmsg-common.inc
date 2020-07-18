DESCRIPTION = "RPMSG benchmark tool that includes both the CM4 firmware and CA application"
AUTHOR = "Dimitris Tassopoulos <dimtass@gmail.com>"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit cmake

SRC_URI = "git://dimtass@bitbucket.org/dimtass/stm32mp1-rpmsg-test.git;protocol=https"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "15ee9bd2590cea74fb2a6504871f7b436b30a08c"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

# NOTE: no Makefile found, unable to determine what needs to be done

do_configure () {
	# Specify any needed configure commands here
	:
}
