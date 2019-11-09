DESCRIPTION = "amlogic power and reboot tools"
SECTION = "base"
LICENSE = "proprietary"

require conf/license/license-gplv2.inc

PV = "1.0"

SRC_URI = " file://shutdown.sh \
    file://amlreboot \
    file://amlhalt \
"

S = "${WORKDIR}"

inherit pkgconfig update-rc.d

do_install() {
    install -d ${D}${sysconfdir}/init.d/
    install -m 0755 ${WORKDIR}/shutdown.sh ${D}${sysconfdir}/init.d/${PN}
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/amlreboot ${D}${bindir}/
    install -m 0755 ${WORKDIR}/amlhalt ${D}${bindir}/
}

FILES_${PN} = "${bindir} ${sysconfdir}"

INITSCRIPT_NAME = "${PN}"
INITSCRIPT_PARAMS = "defaults 08"

do_rm_work() {
}

COMPATIBLE_MACHINE = "^(alien5|k1pro|k2pro|k2prov2|k3pro|k1plus|kvim2|c300|c300pro|c400plus|alien4)$"
