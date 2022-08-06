workspace(name = "data-boxes")

#######################
# JAVA SUPPORT
#######################

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

rules_jvm_external_version = "4.2"

rules_jvm_external_sha256 = "cd1a77b7b02e8e008439ca76fd34f5b07aecb8c752961f9640dea15e9e5ba1ca"

# https://github.com/bazelbuild/rules_jvm_external#readme
http_archive(
    name = "rules_jvm_external",
    sha256 = rules_jvm_external_sha256,
    strip_prefix = "rules_jvm_external-{version}".format(version = rules_jvm_external_version),
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/{version}.zip".format(version = rules_jvm_external_version),
)

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

load("@rules_jvm_external//:defs.bzl", "maven_install")

grpc_version = "1.47.0"

manifold_version = "2022.1.14"

maven_install(
    name = "maven",
    artifacts = [
        # TODO: refine later https://github.com/bazelbuild/rules_jvm_external#compile-only-dependencies
        "systems.manifold:manifold-ext-rt:{version}".format(version = manifold_version),
        "systems.manifold:manifold-ext:{version}".format(version = manifold_version),
        # this follows https://github.com/grpc/grpc-java#download
        "io.grpc:grpc-netty-shaded:{version}".format(version = grpc_version),
        "io.grpc:grpc-protobuf:{version}".format(version = grpc_version),
        "io.grpc:grpc-stub:{version}".format(version = grpc_version),
    ],
    fail_on_missing_checksum = True,
    fetch_sources = False,
    repositories = [
        "https://repo1.maven.org/maven2",
    ],
)

######################
# Golang
######################

go_bazel_version = "0.33.0"

go_bazel_sha256 = "685052b498b6ddfe562ca7a97736741d87916fe536623afb7da2824c0211c369"

http_archive(
    name = "io_bazel_rules_go",
    sha256 = go_bazel_sha256,
    urls = [
        "https://github.com/bazelbuild/rules_go/releases/download/v{version}/rules_go-v{version}.zip".format(version = go_bazel_version),
        "https://mirror.bazel.build/github.com/bazelbuild/rules_go/releases/download/v{version}/rules_go-v{version}.zip".format(version = go_bazel_version),
    ],
)

# # this can get bumped once they fix this: https://github.com/bazelbuild/bazel-gazelle/issues/1217
gazelle_version = "0.25.0"

gazelle_sha256 = "5982e5463f171da99e3bdaeff8c0f48283a7a5f396ec5282910b9e8a49c0dd7e"

http_archive(
    name = "bazel_gazelle",
    sha256 = gazelle_sha256,
    urls = [
        "https://github.com/bazelbuild/bazel-gazelle/releases/download/v{version}/bazel-gazelle-v{version}.tar.gz".format(version = gazelle_version),
        "https://mirror.bazel.build/github.com/bazelbuild/bazel-gazelle/releases/download/v{version}/bazel-gazelle-v{version}.tar.gz".format(version = gazelle_version),
    ],
)

load("@io_bazel_rules_go//go:deps.bzl", "go_register_toolchains", "go_rules_dependencies")

go_rules_dependencies()

go_register_toolchains(version = "1.18")

load("@bazel_gazelle//:deps.bzl", "gazelle_dependencies", "go_repository")

### BEGIN required go import hints ###

# TODO: remove this once this bug is fixed https://github.com/bazelbuild/bazel-gazelle/issues/1217#issuecomment-1121223764
go_repository(
    name = "org_golang_x_mod",
    build_external = "external",
    importpath = "golang.org/x/mod",
    sum = "h1:kQgndtyPBW/JIYERgdxfwMYh3AVStj88WQTlNDi2a+o=",
    version = "v0.6.0-dev.0.20220106191415-9b9b3d81d5e3",
)

go_repository(
    name = "org_golang_x_sys",
    build_external = "external",
    importpath = "golang.org/x/sys",
    version = "v0.0.0-20220610221304-9f5ed59c137d",
)

go_repository(
    name = "org_golang_x_net",
    build_external = "external",
    importpath = "golang.org/x/net",
    sum = "h1:4SFsTMi4UahlKoloni7L4eYzhFRifURQLw+yv0QDCx8=",
    version = "v0.0.0-20220607020251-c690dde0001d",
)

### END required go import hints ###

gazelle_dependencies()

######################
# OTHER
######################

protobuf_version = "21.1"

protobuf_sha = "f1a83673cbcaff6346a8fba87a9c02c0f943a4a696b6c7d1b71586d97609db12"

# requirement of 'com_github_bazelbuild_buildtools'
http_archive(
    name = "com_google_protobuf",
    sha256 = protobuf_sha,
    strip_prefix = "protobuf-{version}".format(version = protobuf_version),
    url = "https://github.com/protocolbuffers/protobuf/archive/v{version}.tar.gz".format(version = protobuf_version),
)

load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")

protobuf_deps()

http_archive(
    name = "com_github_bazelbuild_buildtools",
    strip_prefix = "buildtools-master",
    url = "https://github.com/bazelbuild/buildtools/archive/master.zip",
)

rules_proto_grpc_version = "4.1.1"

rules_proto_grpc_sha = "507e38c8d95c7efa4f3b1c0595a8e8f139c885cb41a76cab7e20e4e67ae87731"

http_archive(
    name = "rules_proto_grpc",
    sha256 = rules_proto_grpc_sha,
    strip_prefix = "rules_proto_grpc-{version}".format(version = rules_proto_grpc_version),
    urls = ["https://github.com/rules-proto-grpc/rules_proto_grpc/archive/{version}.tar.gz".format(version = rules_proto_grpc_version)],
)

load("@rules_proto_grpc//:repositories.bzl", "rules_proto_grpc_repos", "rules_proto_grpc_toolchains")

rules_proto_grpc_toolchains()

rules_proto_grpc_repos()

load("@rules_proto//proto:repositories.bzl", "rules_proto_dependencies", "rules_proto_toolchains")

rules_proto_dependencies()

rules_proto_toolchains()
