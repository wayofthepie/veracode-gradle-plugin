/*******************************************************************************
 * MIT License
 *
 * Copyright (c) 2017 Calgary Scientific Incorporated
 *
 * Copyright (c) 2013-2014 kctang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/

package com.calgaryscientific.gradle

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.testkit.runner.UnexpectedBuildFailure
import spock.lang.Specification
import org.gradle.testkit.runner.GradleRunner
import static org.gradle.testkit.runner.TaskOutcome.*
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.gradle.testfixtures.ProjectBuilder

class TestCommonSetup extends Specification {
    TemporaryFolder testProjectDir
    File buildFile
    Boolean debug
    PrintStream stdout = System.out

    def setup() {
        testProjectDir = new TemporaryFolder()
        testProjectDir.create()
        buildFile = testProjectDir.newFile('build.gradle')
        debug = true
    }

    def cleanup() {
        testProjectDir.delete()
    }

    def executeTask(String... tasks) {
        GradleRunner.create()
                .withDebug(debug)
                .withProjectDir(testProjectDir.root)
                .withPluginClasspath()
                .withArguments(tasks)
                .build()
    }

    def mockSystemOut() {
        def os = new ByteArrayOutputStream()
        System.out = new PrintStream(os)
        return os
    }

    def getSystemOut(def os) {
        def array = os.toByteArray()
        def is = new ByteArrayInputStream(array)
        return is
    }

    def restoreStdout() {
        System.out = stdout
    }

    File getResource(String filename) {
        new File(getClass().classLoader.getResource(filename).toURI())
    }

}