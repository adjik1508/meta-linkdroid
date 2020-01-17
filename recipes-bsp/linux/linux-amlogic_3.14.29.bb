DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "^(k1pro|k2pro|k2prov2|k3pro|k1plus|kvim2|c300|c300pro|c400plus|alien4)$"

inherit kernel machine_kernel_pr

DEPENDS = "xz-native bc-native u-boot-mkimage-native virtual/${TARGET_PREFIX}gcc"

# Avoid issues with Amlogic kernel binary components
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_STRIP = "1"
LINUX_VERSION ?= "3.14.29"
LINUX_VERSION_EXTENSION ?= "amlogic"
LOCALVERSION ?= ""

DEPENDS_append_aarch64 = " libgcc"
KERNEL_CC_append_aarch64 = " ${TOOLCHAIN_OPTIONS}"
KERNEL_LD_append_aarch64 = " ${TOOLCHAIN_OPTIONS}"

S = "${WORKDIR}/linux-amlogic-coreelec-amlogic-3.14-nougat"
B = "${WORKDIR}/build"

DTS = "${@ d.getVar('KERNEL_DEVICETREE').replace('.dtb','.dts') }"

SRC_URI = "https://github.com/OpenVisionE2/linux-amlogic-coreelec/archive/amlogic-3.14-nougat.tar.gz \
  file://defconfig \
  file://kernel-add-support-for-gcc6.patch \
  file://kernel-add-support-for-gcc7.patch \
  file://kernel-add-support-for-gcc9.patch \
  file://${DTS} \
"

SRC_URI[md5sum] = "8ac4213b36d44f356dff16834a2f4b93"
SRC_URI[sha256sum] = "919efa44b576612056eec138738ef142dad2adb7ee3a248b37b09663c61f65de"

do_configure_prepend(){
    sed -i "s/@DISTRONAME@/${MACHINE}/" "${WORKDIR}/defconfig"
}

do_compile_append() {
    install -m 0644 ${WORKDIR}/${DTS} ${S}/arch/arm64/boot/dts/amlogic/
    if test -n "${KERNEL_DEVICETREE}"; then
    	for DTB in ${KERNEL_DEVICETREE}; do
    		if echo ${DTB} | grep -q '/dts/'; then
    			bbwarn "${DTB} contains the full path to the the dts file, but only the dtb name should be used."
    			DTB=`basename ${DTB} | sed 's,\.dts$,.dtb,g'`
    		fi
    		oe_runmake ${DTB}
    	done
    	# Create directory, this is needed for out of tree builds
    	mkdir -p ${B}/arch/arm64/boot/dts/amlogic/
    	cp ${B}/arch/arm64/boot/dts/amlogic/${KERNEL_DEVICETREE} ${B}/arch/arm64/boot/
    fi
}
