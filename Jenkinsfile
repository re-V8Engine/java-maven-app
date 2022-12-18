def gv

pipeline {
    agent any
    tools {
        maven "maven-3.8"
    }
    stages {
        stage('init') {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage('build jar') {
            steps {
                script {
                    gv.buildApp()
                }
            }
        }
        stage('build image') {
            steps {
                script {
                    gv.buildImage()
                }
            }
        }
        stage('deploy') {
            steps {
                script {
                    echo "Deploying..."
                }
            }
        }
    }
}