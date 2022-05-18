load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "2.5"
RULES_JVM_EXTERNAL_SHA = "249e8129914be6d987ca57754516be35a14ea866c616041ff0cd32ea94d2f3a1"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    # name = "maven",
    artifacts = [
        "com.google.code.findbugs:jsr305:1.3.9",
    	"com.google.errorprone:error_prone_annotations:2.0.18",
        "systems.manifold:manifold-ext-rt:2022.1.12",
	],
    fetch_sources = True,   # Fetch source jars. Defaults to False.
    repositories = [
    	"https://repo1.maven.org/maven2",
    ],
)
