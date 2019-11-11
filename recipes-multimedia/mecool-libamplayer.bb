SUMMARY = "Amlogic player library"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/libamcodec-210755d/amplayer"

SRC_URI = "https://raw.githubusercontent.com/OpenVisionE2/amlogic-libs/master/libamcodec-210755d.tar.gz \
           file://libamplayer.pc \
"

SRC_URI[md5sum] = "d2e7dc15302fa64eef54aa67da5f9f34"
SRC_URI[sha256sum] = "79f2ae9c4be27f016314d6ff21f1264c32a73581e5f7c297a7efda6a4cb2df9b"

COMPATIBLE_MACHINE = "^(k1pro|k2pro|k2prov2|k3pro|k1plus|kvim2|c300|c300pro|c400plus|alien4)$"

DEPENDS = "mecool-libamadec mecool-libamcodec"
RDEPENDS_${PN} = "mecool-libamadec mecool-libamcodec"

inherit lib_package

EXTRA_OEMAKE = "\
    'CC=${CC}' \
    'LD=${LD}' \
    'CFLAGS=-fPIC -DENABLE_FREE_SCALE -I${S}/include -I${S}/../amffmpeg -I${S}/player/ \
    -I${S}/player/include -I${S} -I${STAGING_INCDIR}/amlogic/amcodec' \
    'LDFLAGS=-lamadec -lm -lc  -shared -Wl,--shared ' \
"

do_install() {
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/libamplayer.pc ${D}${libdir}/pkgconfig/
    install -d ${D}${includedir}/amlogic/player
    install -d ${D}${libdir}
    install -m 0755  ${S}/libamplayer.so  ${D}${libdir}
    cp -pR  ${S}/player/include/* ${D}${includedir}/amlogic/player
}

FILES_${PN} = "${libdir}/* "
FILES_${PN}-dev = "${includedir}/*"
