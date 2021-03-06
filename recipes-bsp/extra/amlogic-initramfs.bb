# Simple initramfs image. Mostly used for live images.
DESCRIPTION = "Small image capable of booting a device. The kernel includes \
the Minimal RAM-based Initial Root Filesystem (initramfs), which finds the \
first 'init' program more efficiently."

COMPATIBLE_MACHINE = "^(alien5|k1pro|k2pro|k2prov2|k3pro|k1plus|kvim2|c300|c300pro|c400plus|alien4)$"

PACKAGE_INSTALL = "kmod initramfs-boot-am linkdroid-nftl-dev-alien5 parted e2fsprogs busybox udev base-passwd ${ROOTFS_BOOTSTRAP_INSTALL}"

DEPENDS = "linux-${MACHINEKERNEL}"

IMAGE_FEATURES = ""

IMAGE_LINGUAS = ""

LICENSE = "MIT"

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"
inherit core-image

IMAGE_ROOTFS_SIZE = "19192"

BAD_RECOMMENDATIONS += "busybox-syslog"

IMAGE_NAME = "amlogic-initramfs-${MACHINE}"
IMAGE_NAME[vardepsexclude] += "DATE"
IMAGE_ROOTFS = "${TMPDIR}/rootfs-initramfs/${MACHINE}"
PACKAGES = ""
PROVIDES = ""

# disable unneeded tasks
do_shared_workdir[noexec] = "1"
do_package_qa[noexec] = "1"
do_packagedata[noexec] = "1"
do_package_deb[noexec] = "1"
do_package_ipk[noexec] = "1"
do_package_rpm[noexec] = "1"
do_package_tar[noexec] = "1"
do_package_write_deb[noexec] = "1"
do_package_write_ipk[noexec] = "1"
do_package_write_rpm[noexec] = "1"
do_package_write_tar[noexec] = "1"
do_populate_sysroot[noexec] = "1"
