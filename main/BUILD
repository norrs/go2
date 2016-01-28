
#java_library(
#    name = 'generated',
#    srcs = glob(['gen/**/*.java']),
#    deps = [
#        '//external:thrift-j2-core',
#    ],
#)

java_library(
    name = 'libgo2',
    srcs = glob([
            'src/**/*.java'

        ],
        exclude = ['src/no/norrs/go2/Main.java'],
    ),
    resources = glob([
           'webapp/**/*.html',
           'resources/**/*',
    ]),
    deps = [
#        ':generated',
#        '//external:thrift-j2-core',
#        '//external:thrift-j2-jax-rs',
#        '//third-party:javax.servlets',
        '//third-party:args4j_args4j',
        '//third-party:org.eclipse.jetty_jetty-servlet',
        '//third-party:org.eclipse.jetty_jetty-servlets',
        '//third-party:org.eclipse.jetty_jetty-rewrite',
        '//third-party:com.google.http-client_google-http-client',
        '//third-party:redis.clients_jedis',
        '//third-party:com.github.spullara.mustache.java_compiler',
        '//third-party:org.freemarker_freemarker',
    ],
)

java_binary(
    name = 'go2',
    srcs = ['src/no/norrs/go2/Main.java'],
    deps = [
        ':libgo2',
         '//third-party:args4j_args4j',
#        '//third-party:javax.servlets',
         '//third-party:org.slf4j_slf4j-api',
        '//third-party:org.eclipse.jetty_jetty-rewrite',
        '//third-party:org.eclipse.jetty_jetty-servlet',
        '//third-party:org.eclipse.jetty_jetty-servlets',
    ],
    main_class = 'no.norrs.go2.Main',
    visibility = ['//visibility:public'],
)

java_library(
    name = "test_utils",
    srcs = glob(["javatests/**/Abstract*.java"]),
    deps = [
        "//third-party:org.eclipse.jetty.toolchain_jetty-test-helper",
        '//third-party:org.eclipse.jetty_jetty-rewrite',
        '//third-party:org.eclipse.jetty_jetty-servlet',
        '//third-party:org.eclipse.jetty_jetty-servlets',
    ],
)

java_test(
    name = "system_tests",
    size = "small",
    srcs = glob([
       "javatests/**/*.java",
    ], exclude = ['javatests/**/Abstract*Test.java']),
    deps = [
        ":test_utils",
        ":libgo2",
        "//third-party:org.eclipse.jetty.toolchain_jetty-test-helper",
        "//third-party:org.eclipse.jetty_jetty-rewrite",
        #"//third-party:org.eclipse.jetty_jetty-client",
        "//third-party:org.eclipse.jetty_jetty-http",
        #"//third-party:org.eclipse.jetty_jetty-server",
        #"//third-party:org.eclipse.jetty_jetty-util",
        "//third-party:org.eclipse.jetty_jetty-servlets",
        "//third-party:org.eclipse.jetty_jetty-servlet",
        "//third-party:com.github.kstyrc_embedded-redis",
    ],
)
