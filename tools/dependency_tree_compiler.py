#!/usr/bin/env python

def compile_file(dependency_file, dependency_map):
	for line in dependency_file:
		line = line.strip()
		if "---" in line and not "------" in line and not "project :" in line:
			line_content = line.split("---")[1]
			line_content = line_content.strip()
			package = line_content.split(":")[0]
			name = line_content.split(":")[1]
			name = name + " (" + package + ")"
			number = line_content.split(":")[2]
			number = number.strip()
			if " " in number:
				number = number.split(" ")[0]
			dependency_map[name] = "v" + str(number)
		else:
			print("no dependency:", line)
	print(dependency_map)
	return dependency_map

dependency_all_map = compile_file(open("dependencies.app"), {})
dependency_all_map = compile_file(open("dependencies.dtos"), dependency_all_map)
dependency_all_map = compile_file(open("dependencies.server"), dependency_all_map)
print("------------------------------------------------------------------")
print("------------Dependencies for copy paste in google drive-----------")
print("------------------------------------------------------------------")
sorted_keys = sorted(dependency_all_map.keys())

for name in sorted_keys:
	print(name, "	", dependency_all_map[name])

