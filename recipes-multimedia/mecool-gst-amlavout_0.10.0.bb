SUMMARY = "GStreamer AML AVout plugin"
# Freescale rip-off
#  Amlogic GStreamer plugins to send audio es to aml dsp and video es to aml hw decoder. 
#  decode and render will be complete at kernel level.
SECTION = "multimedia"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "^(k1pro|k2pro|k2prov2|k3pro|k1plus|kvim2|c300|c300pro|c400plus|alien4)$"

PARALLEL_MAKE = ""

inherit autotools pkgconfig

DEPENDS = "gstreamer mecool-gst-amlavsink mecool-libamcodec mecool-libamavutils mecool-libamplayer"
RDEPENDS_{PN} = "mecool-libamavutils"

SRC_URI = "https://raw.githubusercontent.com/OpenVisionE2/amlogic-libs/master/gst-aml-plugins-0.10.0.zip"

SRC_URI[md5sum] = "3ff606387aecef5e2dc451fcb7fdc987"
SRC_URI[sha256sum] = "e89795ae64e638845aa1c681e40c9e4059e847fd3855452a8d6824b3f87a16dc"

S = "${WORKDIR}/gst-aml-plugins-0.10.0"

CFLAGS =+ " -O2 -fPIC -I${STAGING_INCDIR} -I${S}/include  -I${S}/include/amports  -I${S}/common/include/ppmgr \
-I${S}/amlaudio -I${S}/amlvideo "
LDFLAGS =+ " -L${STAGING_LIBDIR} -L${STAGING_BASELIBDIR} " 

FILES_${PN} += "${libdir}"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug $"
FILES_${PN}-dev += "${S}/include/* "

do_rm_work(){
}

do_package_qa() {
}
