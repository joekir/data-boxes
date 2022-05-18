load("@rules_java//java:defs.bzl", "java_binary", "java_library")

java_library (
    name = "databox",
    srcs = glob(["src/main/java/com/joek/databoxes/*.java"]),
    visibility = ["//bazelapp:__pkg__"],
	deps = [
		"@maven//:systems_manifold_manifold_ext_rt",
	],
)

java_binary(
    name = "DataBoxApp",
    srcs = glob(["src/main/java/com/joek/databoxes/*.java"]),
    main_class = "com.joek.databoxes.Main",
    deps = ["//:databox"]
)