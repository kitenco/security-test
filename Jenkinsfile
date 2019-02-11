pipeline {
    agent any
    tools {
        maven 'M3'
        jdk 'jdk11'
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }

        stage ('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true install'
            }

        }

        stage ('Dependency Check') {
            steps {
                sh 'mvn org.owasp:dependency-check-maven:check -Ddependency-check-format=XML'
            }
        }

        stage ('Analysis') {
                def mvnHome = tool 'mvn-default'

                sh "${mvnHome}/bin/mvn -batch-mode -V -U -e checkstyle:checkstyle pmd:pmd pmd:cpd findbugs:findbugs spotbugs:spotbugs"

                def checkstyle = scanForIssues tool: [$class: 'CheckStyle'], pattern: '**/target/checkstyle-result.xml'
                publishIssues issues:[checkstyle], unstableTotalAll:1

                def pmd = scanForIssues tool: [$class: 'Pmd'], pattern: '**/target/pmd.xml'
                publishIssues issues:[pmd], unstableTotalAll:1

                def cpd = scanForIssues tool: [$class: 'Cpd'], pattern: '**/target/cpd.xml'
                publishIssues issues:[cpd]

                def findbugs = scanForIssues tool: [$class: 'FindBugs'], pattern: '**/target/findbugsXml.xml'
                publishIssues issues:[findbugs], unstableTotalAll:1

                def spotbugs = scanForIssues tool: [$class: 'SpotBugs'], pattern: '**/target/spotbugsXml.xml'
                publishIssues issues:[spotbugs], unstableTotalAll:1

                def maven = scanForIssues tool: [$class: 'MavenConsole']
                publishIssues issues:[maven]
        }

        stage ('Analysis2') {
            steps {
                sh 'mvn --batch-mode -V -U -e checkstyle:checkstyle pmd:pmd pmd:cpd findbugs:findbugs com.github.spotbugs:spotbugs-maven-plugin:3.1.7:spotbugs'
            }
        }

        stage('Sanity check') {
            steps {
                input "Does the staging environment look ok?"
            }
        }
    }

    post {
        always {

            recordIssues enabledForFailure: true, tool: checkStyle(pattern: 'checkstyle-result.xml')
            recordIssues enabledForFailure: true, tool: spotBugs()
            recordIssues enabledForFailure: true, tool: cpd(pattern: '**/target/cpd.xml')
            recordIssues enabledForFailure: true, tool: pmdParser(pattern: '**/target/pmd.xml')

            dependencyCheckPublisher canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '', unHealthy: ''
        }
    }
}