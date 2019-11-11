SUMMARY = "Amlogic codecs library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/libamcodec-210755d/amcodec"

SRC_URI = "https://raw.githubusercontent.com/OpenVisionE2/amlogic-libs/master/libamcodec-210755d.tar.gz \
           file://libamcodec.pc \
"

SRC_URI[md5sum] = "d2e7dc15302fa64eef54aa67da5f9f34"
SRC_URI[sha256sum] = "79f2ae9c4be27f016314d6ff21f1264c32a73581e5f7c297a7efda6a4cb2df9b"

COMPATIBLE_MACHINE = "^(k1pro|k2pro|k2prov2|k3pro|k1plus|kvim2|c300|c300pro|c400plus|alien4)$"

DEPENDS = "mecool-libamadec"
RDEPENDS_${PN} = "mecool-libamadec"

inherit lib_package pkgconfig

EXTRA_OEMAKE = " \
    'CC=${CC}' \
    'CFLAGS=-O2 -fPIC -I${S}/include -I${S} -I${S}/codec -I${STAGING_INCDIR}/amlogic/amadec ' \
    'LDFLAGS=-lamadec -lm -lc  -shared -Wl,--shared -Wl,-soname,libamcodec.so' \
"

do_install() {
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/libamcodec.pc ${D}${libdir}/pkgconfig/
    install -d ${D}${includedir}/amlogic/amcodec
    install -d ${D}${includedir}/amcodec
    cp -pR ${S}/include/* ${D}${includedir}/amlogic/amcodec
    cp -pR ${S}/include/* ${D}${includedir}/amcodec
    install -d ${D}${libdir}
    install -m 0755  ${S}/libamcodec.so.0.0  ${D}${libdir}
    cd ${D}${libdir}
    ln -sf libamcodec.so.0.0 libamcodec.so
}

do_package_qa() {
}
