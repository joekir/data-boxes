workspace(name = "data-boxes")

#######################
# JAVA SUPPORT
#######################

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

rules_jvm_external_version = "4.2"
RULES_JVM_EXTERNAL_SHA = "cd1a77b7b02e8e008439ca76fd34f5b07aecb8c752961f9640dea15e9e5ba1ca"

# https://github.com/bazelbuild/rules_jvm_external#readme
http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-{version}".format(version = rules_jvm_external_version),
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/{version}.zip".format(version = rules_jvm_external_version),
)

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")
rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")
rules_jvm_external_setup()

load("@rules_jvm_external//:defs.bzl", "maven_install")
maven_install(
    name = "maven",
    artifacts = [
				# TODO: refine later https://github.com/bazelbuild/rules_jvm_external#compile-only-dependencies
        "systems.manifold:manifold-ext-rt:2022.1.14",
        "systems.manifold:manifold-ext:2022.1.14",
    ],
    fetch_sources = False,  # Fetch source jars. Defaults to False.
		# fail_on_missing_checksum = True # add later!
    # maven_install_json = "@data-boxes//3rdparty:maven_install.json",
    repositories = [
        "https://repo1.maven.org/maven2",
    ],
)
