pipeline {
    agent any

    stages {
        stage ('Compile Stage') {
            steps {
                withMaven(maven : 'maven_3_7_0') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage ('Testing Stage') {
            steps {
                withMaven(maven : 'maven_3_7_0') {
                    sh 'mvn clean test'
                }
            }
        }

        stage ('Build Stage') {
            steps {
                withMaven(maven : 'maven_3_7_0') {
                    sh 'mvn clean build'
                }
            }
        }
    }
}