load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-1.2",
    sha256 = "e5c68b87f750309a79f59c2b69ead5c3221ffa54ff9496306937bfa1c9c8c86b",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/1.2.zip"
)


load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    name = "maven",
    artifacts = [
    	"com.google.code.findbugs:jsr305:1.3.9",
    	"com.google.errorprone:error_prone_annotations:2.0.18",
			"systems.manifold:manifold:2022.1.14",
		],
    repositories = [
    	"https://repo1.maven.org/maven2",
    ],
    # fetch_sources = True,   # Fetch source jars. Defaults to False.
)
