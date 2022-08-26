workspace(name = "data-boxes")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

protobuf_version = "21.1"

protobuf_sha = "f1a83673cbcaff6346a8fba87a9c02c0f943a4a696b6c7d1b71586d97609db12"

# requirement of 'com_github_bazelbuild_buildtools'
http_archive(
    name = "com_google_protobuf",
    sha256 = protobuf_sha,
    strip_prefix = "protobuf-{version}".format(version = protobuf_version),
    url = "https://github.com/protocolbuffers/protobuf/archive/v{version}.tar.gz".format(version = protobuf_version),
)

load("@com_google_protobuf//:protobuf_deps.bzl", "PROTOBUF_MAVEN_ARTIFACTS")
load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")

protobuf_deps()

#######################
# JAVA SUPPORT
#######################

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

http_archive(
    name = "io_grpc_grpc_java",
    sha256 = "f2a7ab5148a1f6b451db13c5eea1185341c681c031dc775a973775e1c5d1afcb",
    strip_prefix = "grpc-java-{version}".format(version = "1.48.1"),
    url = "https://github.com/grpc/grpc-java/archive/v{version}.zip".format(version = "1.48.1"),
)

# joek: this approach seems to not work, probably ends in the same error :(
#IO_GRPC_GRPC_JAVA_ARTIFACTS = [
#    "org.apache.tomcat:annotations-api:6.0.53",
#    "io.netty:netty-tcnative-boringssl-static:2.0.53.Final",
#    "io.netty:netty-transport-native-unix-common:4.1.72.Final",
#    "io.netty:netty-transport-native-epoll:jar:linux-x86_64:4.1.77.Final",
#    "io.netty:netty-codec-http2:4.1.77.Final",
#]

load("@io_grpc_grpc_java//:repositories.bzl", "IO_GRPC_GRPC_JAVA_ARTIFACTS")
load("@io_grpc_grpc_java//:repositories.bzl", "IO_GRPC_GRPC_JAVA_OVERRIDE_TARGETS")
load("@io_grpc_grpc_java//:repositories.bzl", "grpc_java_repositories")

# Fu on how to exclude stuff from here
# https://github.com/google/startup-os/blob/master/WORKSPACE#L25
grpc_java_repositories()

grpc_version = "1.47.0"

manifold_version = "2022.1.19"

load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    name = "maven",
    artifacts = [
        # TODO: refine later https://github.com/bazelbuild/rules_jvm_external#compile-only-dependencies
        "systems.manifold:manifold-ext-rt:{version}".format(version = manifold_version),
        "systems.manifold:manifold-ext:{version}".format(version = manifold_version),
        # this follows https://github.com/grpc/grpc-java#download
        #        "io.grpc:grpc-netty-shaded:{version}".format(version = grpc_version),
        "io.grpc:grpc-protobuf:{version}".format(version = grpc_version),
        "io.grpc:grpc-stub:{version}".format(version = grpc_version),
    ] + IO_GRPC_GRPC_JAVA_ARTIFACTS + PROTOBUF_MAVEN_ARTIFACTS,
    fail_on_missing_checksum = True,
    generate_compat_repositories = True,
    override_targets = IO_GRPC_GRPC_JAVA_OVERRIDE_TARGETS,
    repositories = [
        "https://repo1.maven.org/maven2",
        "https://repo.maven.apache.org/maven2/",
    ],
)

load("@maven//:compat.bzl", "compat_repositories")

compat_repositories()

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

go_repository(
    name = "com_github_open_policy_agent_opa",
    importpath = "github.com/open-policy-agent/opa",
    sum = "h1:UKTpyFUPMs4wYYL1qsXpYQQurBCPoEjFbxaD/4V46gY=",
    version = "v0.43.0",
)

go_repository(
    name = "com_github_rcrowley_go_metrics",
    importpath = "github.com/rcrowley/go-metrics",
    sum = "h1:N/ElC8H3+5XpJzTSTfLsJV/mx9Q9g7kxmchpfZyxgzM=",
    version = "v0.0.0-20201227073835-cf1acfcdf475",
)

go_repository(
    name = "com_github_pkg_errors",
    importpath = "github.com/pkg/errors",
    sum = "h1:FEBLx1zS214owpjy7qsBeixbURkuhQAwrK5UwLGTwt4=",
    version = "v0.9.1",
)

go_repository(
    name = "com_github_ghodss_yaml",
    importpath = "github.com/ghodss/yaml",
    sum = "h1:wQHKEahhL6wmXdzwWG11gIVCkOv05bNOh+Rxn0yngAk=",
    version = "v1.0.0",
)

go_repository(
    name = "in_gopkg_yaml_v2",
    importpath = "gopkg.in/yaml.v2",
    sum = "h1:D8xgwECY7CYvx+Y2n4sBz93Jn9JRvxdiyyo8CTfuKaY=",
    version = "v2.4.0",
)

go_repository(
    name = "com_github_xeipuuv_gojsonreference",
    importpath = "github.com/xeipuuv/gojsonreference",
    sum = "h1:EzJWgHovont7NscjpAxXsDA8S8BMYve8Y5+7cuRE7R0=",
    version = "v0.0.0-20180127040603-bd5ef7bd5415",
)

go_repository(
    name = "com_github_xeipuuv_gojsonpointer",
    importpath = "github.com/xeipuuv/gojsonpointer",
    sum = "h1:zGWFAtiMcyryUHoUjUJX0/lt1H2+i2Ka2n+D3DImSNo=",
    version = "v0.0.0-20190905194746-02993c407bfb",
)

go_repository(
    name = "com_github_oneofone_xxhash",
    importpath = "github.com/OneOfOne/xxhash",
    sum = "h1:31czK/TI9sNkxIKfaUfGlU47BAxQ0ztGgd9vPyqimf8=",
    version = "v1.2.8",
)

go_repository(
    name = "com_github_vektah_gqlparser",
    importpath = "github.com/vektah/gqlparser",
    sum = "h1:8b0IcD3qZKWJQHSzynbDlrtP3IxVydZ2DZepCGofqfU=",
    version = "v1.3.1",
)

go_repository(
    name = "com_github_vektah_gqlparser_v2",
    importpath = "github.com/vektah/gqlparser/v2",
    sum = "h1:O0G2I4xEi7J0/b/qRCWGNXEiU9EQ+hGBmlIU1LXLUfY=",
    version = "v2.4.8",
)

go_repository(
    name = "com_github_agnivade_levenshtein",
    importpath = "github.com/agnivade/levenshtein",
    sum = "h1:QY8M92nrzkmr798gCo3kmMyqXFzdQVpxLlGPRBij0P8=",
    version = "v1.1.1",
)

go_repository(
    name = "com_github_gobwas_glob",
    importpath = "github.com/gobwas/glob",
    sum = "h1:A4xDbljILXROh+kObIiy5kIaPYD8e96x1tgBhUI5J+Y=",
    version = "v0.2.3",
)

go_repository(
    name = "com_github_yashtewari_glob_intersection",
    importpath = "github.com/yashtewari/glob-intersection",
    sum = "h1:6gJvMYQlTDOL3dMsPF6J0+26vwX9MB8/1q3uAdhmTrg=",
    version = "v0.1.0",
)

gazelle_dependencies()

######################
# OTHER
######################

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

rules_proto_grpc_repos()

rules_proto_grpc_toolchains()

load("@rules_proto//proto:repositories.bzl", "rules_proto_dependencies", "rules_proto_toolchains")

rules_proto_dependencies()

rules_proto_toolchains()
