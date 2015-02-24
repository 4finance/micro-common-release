package com.ofg.infra.release

import com.github.zafarkhaja.semver.Version
import spock.lang.Specification
import spock.lang.Unroll

class ReleaseVersionParserSpec extends Specification {

    def "should parse version"() {
        given:
            def parser = new ReleaseVersionParser()
        when:
            Version parsedVersion = parser.parseMessage("[#RELEASE-0.6.1]")
        then:
            parsedVersion == new Version.Builder("0.6.1").build()
    }

    @Unroll
    def "should parse version '#commitMessage'"() {
        given:
            def parser = new ReleaseVersionParser()
        when:
            Version parsedVersion = parser.parseMessage(commitMessage)
        then:
            parsedVersion == new Version.Builder(expectedVersion).build()
        where:
            commitMessage         || expectedVersion
            "[#RELEASE-0.6.1]"    || "0.6.1"
            "[#RELEASE-10.16.12]" || "10.16.12"
    }

    @Unroll
    def "should not find bad encoded version '#commitMessage'"() {
        expect:
            new ReleaseVersionParser().parseMessage(commitMessage) == null
        where:
            commitMessage << ["[RELEASE-0.6.1]", "[#RELEASE0.6.1]", "[#RELASE-0.6.1]", "#RELEASE-0.6.1]", "[#RELEASE-0.6.1", "[#RELEASE-0.6]",
                              "[#RELEASE-0.6.1a]", "[#RELEASE 0.6.1]", "#RELEASE-0.6.1", "(#RELEASE-0.6.1)", "[%RELEASE-0.6.1]"]
    }

    def "should parse version in first line of multiline text"() {
        expect:
            parseVersionInString("0.6.1") == new ReleaseVersionParser().parseMessage("""Release trigger [#RELEASE-0.6.1]

Second part""")
    }

    def "should parse version in details section"() {
        expect:
            parseVersionInString("0.6.1") == new ReleaseVersionParser().parseMessage("""Release trigger

Version [#RELEASE-0.6.1].""")
    }

    private Version parseVersionInString(String expectedVersion) {
        new Version.Builder(expectedVersion).build()
    }

    //TODO: Add cases with leading zeros ("[#RELEASE-01.06.02]") and multiple occerences
}
