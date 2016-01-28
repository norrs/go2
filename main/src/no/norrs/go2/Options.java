package no.norrs.go2;


import org.kohsuke.args4j.Option;

public class Options {

    @Option(name = "--port",
            usage = "Port number for HTTP service.",
            metaVar = "port")
    public int port = 8080;

    @Option(name = "--redis",
            usage = "Redis host:port",
            metaVar = "redis")
    public String redis = "localhost:6379";

    @Option(name = "--help",
            usage = "Shows this help message",
            help = true)
    public boolean help = false;
}
