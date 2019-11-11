SUMMARY = "Amlogic audio decoders library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/libamcodec-210755d/amadec"

SRC_URI = "https://raw.githubusercontent.com/OpenVisionE2/amlogic-libs/master/libamcodec-210755d.tar.gz \
           file://libamadec.pc \
"

SRC_URI[md5sum] = "d2e7dc15302fa64eef54aa67da5f9f34"
SRC_URI[sha256sum] = "79f2ae9c4be27f016314d6ff21f1264c32a73581e5f7c297a7efda6a4cb2df9b"

COMPATIBLE_MACHINE = "^(k1pro|k2pro|k2prov2|k3pro|k1plus|kvim2|c300|c300pro|c400plus|alien4)$"

DEPENDS = "mecool-libamavutils alsa-lib rtmpdump"
RDEPENDS_${PN} = "ffmpeg"

### for DTS encoder: don't check for stripped & text relocations
INSANE_SKIP_${PN} = "already-stripped textrel"

inherit lib_package pkgconfig

EXTRA_OEMAKE = "\
    'CC=${CC}' \
    'CFLAGS=-O2 -fPIC -pthread -DALSA_OUT -DENABLE_WAIT_FORMAT -I${S}/include -I${S}' \
    'LDFLAGS=-shared -lamavutils -lpthread -lm -lasound -lrt -ldl' \
"

### NOTE: we are installing closed src DTS encoder as well for transcoding
do_install() {
    install -d ${D}${libdir}/pkgconfig
    install -d ${D}${includedir}/amlogic/amadec
    install -d ${D}${nonarch_base_libdir}/firmware
    install -d ${D}${libdir}
    install -m 0755 ${S}/include/* ${D}${includedir}/amlogic/amadec
    install -m 0755 ${S}/libamadec.so ${D}/${libdir}
    install -m 0755 ${S}/acodec_lib/*.so  ${D}/${libdir}
    install -m 0644 ${WORKDIR}/libamadec.pc ${D}${libdir}/pkgconfig/
}

FILES_${PN} = "${libdir}/* ${nonarch_base_libdir}/firmware"
FILES_${PN}-dev = "${includedir}/*"

do_package_qa() {
}
