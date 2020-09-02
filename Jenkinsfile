pipeline {
    agent any

    stages {
        stage ('Compile Stage') {
            tools {
                jdk 'Java 11'
                maven 'Maven 3.7.0'
            }
            steps {
//                 withMaven(maven : 'maven_3_7_0') {
                    sh 'mvn clean compile'
//                 }
            }
        }

        stage ('Testing Stage') {
            tools {
                jdk 'Java 11'
                maven 'Maven 3.7.0'
            }
            steps {
//                 withMaven(maven : 'maven_3_7_0') {
                    sh 'mvn clean test'
//                 }
            }
        }

        stage ('Build Stage') {
            tools {
                jdk 'Java 11'
                maven 'Maven 3.7.0'
            }
            steps {
//                 withMaven(maven : 'maven_3_7_0') {
                    sh 'mvn clean build'
//                 }
            }
        }
    }
}