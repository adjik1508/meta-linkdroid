GST_VERSION = "0.10"

BOX_BRAND = "mecool"

require conf/distro/multilib.inc

MTD_BLACK  = "mmcblk0"
KERNEL_FILE = "kernel.itb"
ROOTFS_FILE = "rootfs.tar.bz2"

PREFERRED_PROVIDER_virtual/kernel = "linux-amlogic"
PREFERRED_VERSION_linux-amlogic = "3.14.29%"

MACHINEKERNEL = "amlogic"

# Amlogic defaults
require conf/machine/include/arm/arch-armv8.inc
require conf/machine/include/soc-family.inc

IMAGE_CLASSES += "sdcard_image-amlogic"
SDCARD_ROOTFS ?= "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ext4"
IMAGE_FSTYPES ?= "amlogicsdimg"
IMAGE_ROOTFS_SIZE = "476562"
IMAGE_EXTRA_SPACE = "0"
IMAGE_OVERHEAD_FACTOR = "1.0"

BB_HASH_IGNORE_MISMATCH = "1"

# Serial console to be used
SERIAL_CONSOLE = "115200 ttyS0"

KERNEL_CLASSES += "aarch64-fitimage"
KERNEL_IMAGETYPE = "fitImage"

UBOOT_ENTRYPOINT = "0x01080000"
UBOOT_LOADADDRESS = "0x01080000"

INITRAMFS_IMAGE = "amlogic-initramfs"

MACHINE_FEATURES = "bluetooth usbhost vfat alsa dvb-c nolcd hdmicec fb emmc nogamma nofhdskin multilib"

DVBPROVIDER = "kernel"
DVBSKYPROVIDER = "kernel"

MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "\
    amlogic-shutdown \
    amlogic-autoscript \
    amlogic-mali \
    amremote \
    firmware-ap6xxx-mecool \
    gst-fluendo-mpegdemux \
    kernel-module-e2-procfs \
    kernel-modules \
    libmad \
    mecool-amlsetfb \
    mecool-dvb-modules-e2procfs \
    mecool-gst-amlavout \
    mecool-gst-amlavsink \
    mecool-gst-app \
    mecool-libamplayer \
    mecool-libamavutils \
    mecool-libamadec \
    mecool-libamcodec \
    mecool-setup \
    "

KERNEL_MODULE_AUTOLOAD += "\
    e2_procfs \
    mali \
    dhd \
    aml \
    aml_fe \
    avl6862 \
    r848a \
    sysfillrect \
    syscopyarea \
    CH341 \
"

PREFERRED_PROVIDER_jpeg = "libjpeg-turbo"
PREFERRED_PROVIDER_jpeg-native = "libjpeg-turbo-native"

DVBMEDIASINK_CONFIG = "--with-wmv --with-pcm --with-eac3 --with-h265 --with-dtsdownmix"

EXTRA_OECONF_append_pn-enigma2 = " --with-alsa --with-alsacard=hw:0 --with-alsamixer=Master --with-fbdev=/dev/fb0 --with-lcddev=/dev/null --with-amlogic"
DEPENDS_append_pn-enigma2 = " libdvbcsa mecool-libamcodec mecool-libamadec"
EXTRA_OECONF_append_pn-tuxtxt-enigma2 = " --with-fbdev=/dev/fb0"
DEPENDS_append_pn-enigma2-plugin-systemplugins-servicemp3 = " mecool-libamcodec "
CC_append_pn-showiframe = " -DHAVE_AMLOGIC -I${STAGING_INCDIR}/amlogic/amcodec -lamcodec"
DEPENDS_append_pn-showiframe = " mecool-libamcodec"

DISTRO_FEATURES_remove = "x11 wayland"

IMAGEDIR ?= "${MACHINE}"

IMAGE_CMD_amlogicsdimg_append = "\
    mkdir -p ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}; \
    mv ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.img ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/${IMAGE_NAME}.img; \
    echo ${IMAGE_NAME} > ${DEPLOY_DIR_IMAGE}/${IMAGEDIR}/imageversion; \
    cd ${DEPLOY_DIR_IMAGE}; \
    zip ${IMAGE_NAME}_sdcard.zip ${IMAGEDIR}/*; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.ext4; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.manifest; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.json; \
    rm -f ${DEPLOY_DIR_IMAGE}/*.img; \
    rm -Rf ${IMAGEDIR}; \
    "

# We know about rtc
include conf/machine/include/build-extras.inc
