
# Use the meta-st-openstlinux/recipes-samples/images/st-example-image-qt.bb
# as base image and override/add your stuff
require recipes-samples/images/st-example-image-qt.bb

LICENSE = "Proprietary"

CORE_IMAGE_EXTRA_INSTALL_append = " packagegroup-core-ssh-openssh"

IMAGE_FEATURES_remove = "ssh-server-dropbear"

IMAGE_FEATURES += " \
            ssh-server-openssh \
            package-management \
            "

IMAGE_INSTALL = " \
            stm32mp1-rpmsg-test-linux \
            stm32mp1-rpmsg-test-stm32 \
            "

CORE_IMAGE_EXTRA_INSTALL += " \
    ${@bb.utils.contains('BOOTSCHEME_LABELS', 'optee', 'packagegroup-optee-core', '', d)}   \
    ${@bb.utils.contains('BOOTSCHEME_LABELS', 'optee', 'packagegroup-optee-test', '', d)}   \
    "