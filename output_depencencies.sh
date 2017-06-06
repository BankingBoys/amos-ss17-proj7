#!/bin/bash
gradle :app:dependencies > tools/dependencies.app
gradle :dtos:dependencies > tools/dependencies.dtos
gradle :server:dependencies > tools/dependencies.server

cd tools
python dependency_tree_compiler.py
