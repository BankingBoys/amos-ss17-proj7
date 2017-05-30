#!/bin/bash
gradle :android:app:dependencies > tools/dependencies.app
gradle :android:dependencies > tools/dependencies.android
gradle :dtos:dependencies > tools/dependencies.dtos
gradle :server:dependencies > tools/dependencies.server

cd tools
python dependency_tree_compiler.py
