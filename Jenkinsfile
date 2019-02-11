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
            sh 'mvn org.owasp:dependency-check-maven:check -Ddependency-check-format=XML'
            step([$class: 'DependencyCheckPublisher', unstableTotalAll: '0'])
        }
    }
}