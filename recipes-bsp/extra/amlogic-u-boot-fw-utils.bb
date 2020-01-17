HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"
SUMMARY = "U-Boot bootloader fw_printenv/setenv utilities"

DEPENDS = "mtd-utils"

inherit uboot-config

PE = "1"

# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRCREV = "a705ebc81b7f91bbd0ef7c634284208342901149"

SRC_URI = "git://git.denx.de/u-boot.git"

SRC_URI += "file://default-gcc.patch"

S = "${WORKDIR}/git"

UBOOT_MACHINE = "odroid-c2_defconfig"


EXTRA_OEMAKE_class-target = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${CC} ${CFLAGS} ${LDFLAGS}" HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}" V=1'

do_compile () {
	oe_runmake ${UBOOT_MACHINE}
	oe_runmake env
}

do_install () {
	install -d ${D}${sbindir}
	install -m 755 ${S}/tools/env/fw_printenv ${D}${sbindir}/fw_printenv
	install -m 755 ${S}/tools/env/fw_printenv ${D}${sbindir}/fw_setenv
	install -d ${D}${sysconfdir}
	echo "" >> ${D}${sysconfdir}/fw_env.config
}

FILES_${PN} = "${sysconfdir} ${sbindir}"
