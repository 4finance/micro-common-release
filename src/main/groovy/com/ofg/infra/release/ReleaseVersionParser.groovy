package com.ofg.infra.release

import com.github.zafarkhaja.semver.Version

class ReleaseVersionParser {

    private static final RELEASE_VERSION_PATTERN = ~/(?ms).*\[#RELEASE-(\d+.\d+.\d+)\].*/

    Version parseMessage(String message) {
        def matcher = RELEASE_VERSION_PATTERN.matcher(message)
        if (matcher.matches()) {
            String versionString = matcher[0][1]
            return new Version.Builder(versionString).build()
        } else {
            return null
        }
    }
}
