#!/bin/sh

TEMP_DIR=temp

echo "Building deb package"

echo "Creating directory tree"
mkdir -p $TEMP_DIR
mkdir -p $TEMP_DIR/usr/
mkdir -p $TEMP_DIR/etc/
mkdir -p $TEMP_DIR/usr/bin/
mkdir -p $TEMP_DIR/usr/share/
mkdir -p $TEMP_DIR/usr/share/applications/
mkdir -p $TEMP_DIR/DEBIAN

# Change permissions (linting purposes)
chmod 755 $TEMP_DIR/usr/
chmod 755 $TEMP_DIR/usr/bin/
chmod 755 $TEMP_DIR/etc/
chmod 755 $TEMP_DIR/usr/share/
chmod 755 $TEMP_DIR/usr/share/applications/

# Copy control file for DEBIAN/
cp packing/src/DEBIAN/control $TEMP_DIR/DEBIAN/

# conffiler setup for DEBIAN
cp packing/src/DEBIAN/conffiles $TEMP_DIR/DEBIAN/

# Copy binary into place
cp p2pChess/target/p2pChess-1.0.jar $TEMP_DIR/usr/bin/
chmod 644 $TEMP_DIR/usr/bin/p2pChess-1.0.jar # Linting

# .desktop file into place
cp packing/src/chess.desktop $TEMP_DIR/usr/share/applications/
chmod 644 $TEMP_DIR/usr/share/applications/chess.desktop

# Configuration file in place
cp packing/src/chess.conf $TEMP_DIR/etc/
chmod 644 $TEMP_DIR/etc/chess.conf # Linting

# post, rm scripts for DEBIAN/
cp packing/src/DEBIAN/postinst $TEMP_DIR/DEBIAN/
cp packing/src/DEBIAN/prerm $TEMP_DIR/DEBIAN/
cp packing/src/DEBIAN/postrm $TEMP_DIR/DEBIAN/

# Build deb file by doing dpkg-deb xxxx
dpkg-deb --root-owner-group --build $TEMP_DIR
mv $TEMP_DIR.deb chess-v1.0.0.deb

echo "Done."
