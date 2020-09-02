pipeline {
    agent any

    stages {
        stage ('Compile Stage') {
            steps {
                withMaven(maven: 'mvn') {
                    sh 'mvn clean compile'
                }
            }
        }

        stage ('Testing Stage') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage ('Build Stage') {
            steps {
                sh 'mvn clean build'
            }
        }
    }
}